import multiprocessing
import socket
import os
import pickle
from multiprocessing import *

# Коллекция компакт-дисков
collection = [
    {'title': 'Album5', 'year': 2020, 'artist': 'Artist5'},
    {'title': 'Album1', 'year': 2000, 'artist': 'Artist1'},
    {'title': 'Album2', 'year': 1999, 'artist': 'Artist2'},
    {'title': 'Album3', 'year': 2010, 'artist': 'Artist3'},
    {'title': 'Album4', 'year': 2015, 'artist': 'Artist4'}
]
shared_collection=multiprocessing.Manager().list(collection)
CONNECTION_COUNTER = Value('i',0)
HOST = ''  # Symbolic name meaning all available interfaces
PORT = 8901  # Arbitrary non-privileged port


def handle_client(client_socket, addr):
    """
    Обработчик клиентского соединения
    """
    global shared_collection
    try:
        with CONNECTION_COUNTER.get_lock():
            CONNECTION_COUNTER.value += 1
        print("Количество клиентов онлайн: "+str(CONNECTION_COUNTER.value))
        while True:
            # Получить данные от клиента
            data = client_socket.recv(1024)

            # Преобразовать данные в объект Python
            request = pickle.loads(data)
            print("От клиента " + str(addr) + " было принято: " + str(request))

            # Обработать запрос

            if request['action'] == 'list':
                collection_to_send=[]
                for disk in shared_collection:
                    collection_to_send.append(disk)
                response = collection_to_send
            elif request['action'] == 'add':
                shared_collection.append(request['data'])
                response = 'OK'
            elif request['action'] == 'delete':
                for cd in shared_collection:
                    if cd['title'] == request['data']:
                        shared_collection.remove(cd)
                        response = 'OK'
                        break
                else:
                    response = 'Not found'
            elif request['action'] == 'sort':
                field = request['data']
                shared_collection=sorted(shared_collection, key=lambda k: k[field])
                print(shared_collection)
                response = 'OK'
            elif request['action'] == "exit":
                # Закрыть соединение
                response = 'closed'
            else:
                response = 'Invalid request'
            # Отправить данные обратно клиенту
            response_data = pickle.dumps(response)
            print("Клиенту " + str(addr) + " были отправлены данные: " + str(response))
            client_socket.send(response_data)
            if response=='closed':
                break
    except:
        print("Ошибка при работе с клиентом "+str(addr))
    finally:
        client_socket.close()
        with CONNECTION_COUNTER.get_lock():
            CONNECTION_COUNTER.value -= 1
            print("Соединение с клиентом "+str(addr)+" окончено")
            print("Количество клиентов онлайн: "+str(CONNECTION_COUNTER.value))


def main():
    # Create a socket object
    global PORT
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        # Bind the socket to a local address and port
        s.bind((HOST, PORT))
        # Listen for incoming connections
        s.listen()
        print(f'Server listening on port {PORT}')

        while True:
            # Wait for a client to connect
            conn, addr = s.accept()
            print(f'Connected by {addr}')

            # Fork a new process to handle the client's input
            pid = os.fork()
            if pid == 0:
                # Child process
                handle_client(conn, addr)
                os._exit(0)
            else:
                # Parent process
                conn.close()


if __name__ == '__main__':
    main()
