import socket
import pickle
import multiprocessing

def send_request(action, data='none'):
    """
    ��������� ������ �� ������
    """

    # ������� ������ � ���� ������� Python
    request = {'action': action, 'data': data}

    # ������������� ������ � ������� ������ pickle
    request_data = pickle.dumps(request)

    # ��������� ������ �� ������
    client_socket.send(request_data)

    # �������� ����� �� �������
    response_data = client_socket.recv(1024)

    # ��������������� ����� � ������� ������ pickle
    response = pickle.loads(response_data)

    return response

if __name__ == '__main__':
    # ������� TCP ����� � ������������ � �������
    ip = '127.0.1.1'
    port = 8901
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    client_socket.connect((ip, port))
    # ������� ����
    while True:
        print('1. �������� ������ ������')
        print('2. ���������� ����� � ���������')
        print('3. �������� ����� �� ���������')
        print('4. ���������� ��������� �� ����')
        print('5. ���������� ��������� �� ��������')
        print('6. �����')

        # �������� ����� ������������
        choice = int(input('������� ����� ��������: '))

        # ��������� ��������� ��������
        if choice == 1:
            disks = send_request('list')
            for disk in disks:
                print(f'{disk["title"]} ({disk["year"]}), {disk["artist"]}')
        elif choice == 2:
            title = input('������� �������� �����: ')
            year = int(input('������� ��� �������: '))
            artist = input('������� �����������: ')
            disk = {'title': title, 'year': year, 'artist': artist}
            result = send_request('add', disk)
            if result == 'OK':
                print('���� �������� � ���������')
        elif choice == 3:
            title = input('������� �������� �����, ������� ����� �������: ')
            result = send_request('delete', title)
            if result == 'OK':
                print('���� ������ �� ���������')
            else:
                print('���� �� ������ � ���������')
        elif choice == 4:
            result = send_request('sort', 'year')
            if result == 'OK':
                print('����� ���� ������������� � ���������')
            else:
                print('����� �� ���� ������������� � ���������')
        elif choice == 5:
            result = send_request('sort', 'title')
            if result == 'OK':
                print('����� ���� ������������� � ���������')
            else:
                print('����� �� ���� ������������� � ���������')
        elif choice == 6:
            send_request("exit",'none')
            break
        else:
            print('�������� �����. ���������� ��� ���.')
