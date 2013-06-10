﻿# coding=utf-8

from socket import *
from thread import start_new_thread
import mysql

host = ''
port = 52301
msglen = 8192
encoding = 'UTF-8'
backlog = 5

# ProjectAdoctor
# AjrPotccoe,13trdo20!

# 사용자 연결 핸들러 정의
def handler(clientsock, addr):
    print u'\n────────────────────'
    print u'\n○ ', addr
    try:
        # 네트워킹
        unpacker = msgpack.Unpacker()
        while True:
            packet = clientsock.recv(msglen)
            if not packet: break;
            unpacker.feed(packet)
        clientsock.close()
        msg = unpacker.unpack()
        print u'\n', repr(msg)

        # 디시리얼라이즈
        version = msg['version']
        print u'\nversion :', version

        data = msg['data']

        pref = data['pref']
        print u'data\t┬ pref\t┬ age :', pref['age']
        print u'\t│\t│ job :', pref['job']
        print u'\t│\t└ sex :', pref['sex']
        print u'\t│'

        logs = data['logs']
        print u'\t└ logs\t:', len(logs)
        for log in logs:
            print u'\t\t', log

    except Exception as e:
        print addr, u'\nConnection aborted by an error (', e.message, u')'

# Main 함수
# 리스너 소켓을 등록하여, 사용자 연결을 대기한다
# 사용자 연결이 들어올경우 해당 사용자에 대한 스레드를 하나 생성하고,
# 다시 다음 사용자 입력을 기다린다
if __name__ == '__main__':
    serversock = socket(AF_INET, SOCK_STREAM)
    serversock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    serversock.bind((host, port))
    serversock.listen(backlog)
    print u'● 서버 작동시작'
    while 1:
        clientsock, (addr, port) = serversock.accept()
        start_new_thread(handler, (clientsock, addr))
