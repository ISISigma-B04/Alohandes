from datetime import datetime
from random import choice, randint
from threading import Thread
from faker import Faker
from faker.providers import lorem
from faker.providers import company
import json
import cx_Oracle


class Populator:
    connection_url: str
    connection_driver_name: str
    connection_user_name: str
    connection_password: str
    config_path = ''
    fake = Faker()

    def __init__(self):
        cx_Oracle.init_oracle_client(lib_dir=r"C:\Oracle\instantclient_21_10")
        config_loaded = self.__load_json_config()
        if config_loaded:
            print("Bien")
        else:
            print("Mal")

    def __load_json_config(self) -> bool:
        return True

    def populate_a_usuario(self):
        connection = self.get_connection()
        cursor = connection.cursor()
        insertados = []
        for i in range(0, 33000):
            id = self.fake.unique.random_int(min=0, max=1000000)
            tipoid = self.fake.random_choices(elements=('CARNET_U', 'CEDULA', 'PASAPORTE'))
            login = self.fake.user_name()
            relacionu = self.fake.word()
            cursor.execute(
                f"INSERT INTO a_usuario (id,tipoid,login,relacionu) VALUES ({id},'{tipoid}','{login}','{relacionu}')")
            insertados.append(id)
        connection.commit()
        connection.close()
        return insertados

    def populate_a_operador(self, usuarios):
        connection = self.get_connection()
        cursor = connection.cursor()
        for i in range(0, 33000):
            id = choice(usuarios)[0]
            numero_rnt = self.fake.unique.random_int(min=0, max=1000000)
            vencimiento_rnt = self.fake.date_between(start_date='-1y', end_date='today')
            registro_super_turismo = self.fake.word()
            vencimiento_registro_st = self.fake.date_between(start_date='-1y', end_date='today')
            categoria = self.fake.random_element(elements=('HOTEL', 'HOSTAL', 'P_NATURAL', 'APARTAMENTO', 'VECINOS',
                                                           'VIVIENDA_U'))
            direccion = self.fake.address().replace("'", "''")
            hora_apertura = self.fake.time_object(end_datetime=None).time()
            hora_cierre = self.fake.time_object(end_datetime=None).time()
            tiempo_minimo = self.fake.random_int(min=1, max=24)
            ganancia_anio_actual = self.fake.pydecimal(left_digits=5, right_digits=2, positive=True)
            ganancia_anio_corrido = self.fake.pydecimal(left_digits=5, right_digits=2, positive=True)

            cursor.execute(f"INSERT INTO a_operador (id, numero_rnt, vencimiento_rnt, registro_super_turismo, " +
                           f"vencimiento_registro_st, categoria, direccion, hora_apertura, hora_cierre, tiempo_minimo, " +
                           f"ganancia_anio_actual, ganancia_anio_corrido)" +
                           f" VALUES ('{id}', {numero_rnt}, TO_DATE('{vencimiento_rnt}', 'YYYY-MM-DD')," +
                           f" '{registro_super_turismo}', TO_DATE('{vencimiento_registro_st}', 'YYYY-MM-DD')," +
                           f" '{categoria}', '{direccion}', TO_DATE('{hora_apertura}', 'HH24:MI:SS'),"
                           f" TO_DATE('{hora_cierre}','HH24:MI:SS'), {tiempo_minimo}, {ganancia_anio_actual},"
                           f" {ganancia_anio_corrido})")

        connection.commit()
        connection.close()

    def populate_a_oferta(self, usuarios):
        connection = self.get_connection()
        cursor = connection.cursor()
        insertados = []
        for i in range(0, 33000):
            id = self.fake.unique.random_int(min=0, max=1000000)
            capacidad = self.fake.random_int(min=1, max=100)
            precio = self.fake.random_int(min=1, max=1000)
            tamanio = self.fake.random_int(min=1, max=1000)
            dias_reservados = self.fake.random_int(min=1, max=30)
            fecha_creacion = self.fake.date_between(start_date='-1y', end_date='today')
            piso = self.fake.random_int(min=1, max=10)
            habilitada = self.fake.random_int(min=0, max=1)
            operador = choice(usuarios)

            cursor.execute(
                f"INSERT INTO a_oferta (id, capacidad, precio, tamanio, dias_reservados, fecha_creacion, piso," +
                f" habilitada, operador) "
                f"VALUES ({id}, {capacidad}, {precio}, {tamanio}, {dias_reservados}," +
                f" TO_DATE('{fecha_creacion}', 'YYYY-MM-DD'), {piso}, {habilitada}, '{operador}')")
            insertados.append(id)

        connection.commit()
        connection.close()
        return insertados

    def populate_a_reservacolectiva(self):
        connection = self.get_connection()
        cursor = connection.cursor()
        insertados = ['null'] * 33000
        for i in range(0, 100):
            id = self.fake.unique.random_int(min=0, max=100)
            fecha_inicio = self.fake.date_between(start_date='-1y', end_date='+1y')
            duracion = self.fake.random_int(min=1, max=30)
            cantidad = self.fake.random_int(min=1, max=10)
            tipo = self.fake.random_element(elements=('APARTAMENTO', 'HABITACION'))
            cliente = self.fake.random_int(min=1, max=10000)
            cursor.execute(
                f"INSERT INTO a_reservacolectiva (id, fecha_inicio, duracion, cantidad, tipo, cliente) VALUES ({id}," +
                f" '{fecha_inicio}', {duracion}, {cantidad}, '{tipo}', {cliente})")
            insertados[i] = id
        connection.commit()
        connection.close()
        return insertados

    def populate_a_reserva(self, ofertas, colectivas):
        connection = self.get_connection()
        cursor = connection.cursor()
        for i in range(0, 33000):
            id = self.fake.unique.random_int(min=0, max=1000000)
            fecha_inicio = self.fake.date_between(start_date='-10y', end_date='+1y')
            fecha_fin = self.fake.date_between(start_date=fecha_inicio, end_date='+1m')
            personas = self.fake.random_int(min=1, max=10)
            fin_cancelacion_oportuna = self.fake.date_between(start_date='-30d', end_date=fecha_inicio)
            porcentaje_a_pagar = self.fake.random_int(min=0, max=100)
            monto_total = self.fake.random_int(min=100, max=1000)
            propiedad = choice(ofertas)
            colectiva = choice(colectivas)[0]

            cursor.execute(
                f"INSERT INTO a_reserva (id, fecha_inicio, fecha_fin, personas, fin_cancelacion_oportuna," +
                f" porcentaje_a_pagar, monto_total, propiedad, colectiva)" +
                f"VALUES ({id}, '{fecha_inicio}', '{fecha_fin}', {personas}, '{fin_cancelacion_oportuna}'," +
                f"{porcentaje_a_pagar}, {monto_total}, {propiedad}, {colectiva})")

        connection.commit()
        connection.close()

    def get_connection(self):
        dsn_tns = cx_Oracle.makedsn('fn4.oracle.virtual.uniandes.edu.co', '1521', service_name='prod') # if needed, place an 'r' before any parameter in order to address special characters such as '\'.
        conn = cx_Oracle.connect(user=r'ISIS2304B28202310', password='LbyCVTywUGkk', dsn=dsn_tns) # if needed, place an 'r' before any parameter in order to address special characters such as '\'. For example, if your user name contains '\', you'll need to place 'r' before the user name: user=r'User Name'
        return conn
    
populator = Populator()
connection = populator.get_connection()
cursor = connection.cursor()
usuarios = populator.populate_a_usuario()
populator.populate_a_operador(usuarios)
oferta = populator.populate_a_oferta(usuarios)
connection.close()
