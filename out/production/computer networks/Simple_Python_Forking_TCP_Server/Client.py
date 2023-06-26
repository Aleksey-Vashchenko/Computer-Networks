import socket
import pickle
import multiprocessing

def send_request(action, data='none'):
    """
    Отправить запрос на сервер
    """

    # Создать запрос в виде словаря Python
    request = {'action': action, 'data': data}

    # Сериализовать запрос с помощью модуля pickle
    request_data = pickle.dumps(request)

    # Отправить данные на сервер
    client_socket.send(request_data)

    # Получить ответ от сервера
    response_data = client_socket.recv(1024)

    # Десериализовать ответ с помощью модуля pickle
    response = pickle.loads(response_data)

    return response

if __name__ == '__main__':
    # Создать TCP сокет и подключиться к серверу
    ip = '127.0.1.1'
    port = 8901
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((ip, port))
    # Вывести меню
    while True:
        print('1. Просмотр списка дисков')
        print('2. Добавление диска в коллекцию')
        print('3. Удаление диска из коллекции')
        print('4. Сортировка коллекции по году')
        print('5. Сортировка коллекции по названию')
        print('6. Выход')

        # Получить выбор пользователя
        choice = int(input('Введите номер действия: '))

        # Выполнить выбранное действие
        if choice == 1:
            disks = send_request('list')
            for disk in disks:
                print(f'{disk["title"]} ({disk["year"]}), {disk["artist"]}')
        elif choice == 2:
            title = input('Введите название диска: ')
            year = int(input('Введите год выпуска: '))
            artist = input('Введите исполнителя: ')
            disk = {'title': title, 'year': year, 'artist': artist}
            result = send_request('add', disk)
            if result == 'OK':
                print('Диск добавлен в коллекцию')
        elif choice == 3:
            title = input('Введите название диска, который нужно удалить: ')
            result = send_request('delete', title)
            if result == 'OK':
                print('Диск удален из коллекции')
            else:
                print('Диск не найден в коллекции')
        elif choice == 4:
            result = send_request('sort', 'year')
            if result == 'OK':
                print('Диски были отсортированы в коллекции')
            else:
                print('Диски не были отсортированы в коллекции')
        elif choice == 5:
            result = send_request('sort', 'title')
            if result == 'OK':
                print('Диски были отсортированы в коллекции')
            else:
                print('Диски не были отсортированы в коллекции')
        elif choice == 6:
            send_request("exit",'none')
            break
        else:
            print('Неверный выбор. Попробуйте еще раз.')
