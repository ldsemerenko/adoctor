﻿# coding=utf-8

from socket import *
from thread import start_new_thread

host = ''
port = 52301
backlog = 5
recvlen = 1024

def response(key):
    return key

# 사용자 연결 핸들러 정의
def handler(clientsock, addr):
    while 1:
        data = clientsock.recv(recvlen)
        if not data: break
        print repr(addr) + ' recv:' + repr(data)
        clientsock.send(response(data))
        print repr(addr) + ' sent:' + repr(response(data))

    clientsock.close()
    print repr(addr), '- closed connection'

# Main 함수
# 리스너 소켓을 등록하여, 사용자 연결을 대기한다
# 사용자 연결이 들어올경우 해당 사용자에 대한 스레드를 하나 생성하고,
# 다시 다음 사용자 입력을 기다린다
if __name__ == '__main__':
    serversock = socket(AF_INET, SOCK_STREAM)
    serversock.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    serversock.bind((host, port))
    serversock.listen(backlog)
    while 1:
        print 'waiting ...',
        clientsock, addr = serversock.accept()
        print 'connected from:', addr
        start_new_thread(handler, (clientsock, addr))