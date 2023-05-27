from datetime import datetime
from random import choice, randint
from threading import Thread
from faker.providers import lorem
from faker.providers import company
from faker import Faker
import json
import cx_Oracle

class Populator:
    connection_url: str
    connection_driver_name: str
    connection_user_name: str
    connection_password: str
    config_path = 'config.json'
    fake = Faker()

    def __init__(self):
        cx_Oracle.init_oracle_client(lib_dir=r".\instantclient_21_3")
        config_loaded = self.__load_json_config()
        if config_loaded:
            print("Bien")
        else:
            print("Mal")

    def __load_json_config(self) -> bool:
        try:
            with open(self.config_path) as json_file:
                data = json.load(json_file)
                self.connection_url = data['connectionURL']
                self.connection_driver_name = data['connectionDriverName']
                self.connection_user_name = data['connectionUserName']
                self.connection_password = data['connectionPassword']
                return True
        except:
            return False

    def populate(self):
        cursor = cx_Oracle.connect(user=self.connection_user_name, password=self.connection_password,
                                   dsn=self.connection_url).cursor()
        self.__populate_a_usuario(cursor)
        self.__populate_a_reserva(cursor)
        self.__populate_a_oferta(cursor)

    def __populate_a_usuario(self, cursor):
        for i in range(0, 33000):
            id=self.fake.unique.random_int(min=0, max=1000000)
            tipoid=self.fake.random_choices(elements=('CARNET_U', 'CEDULA', 'PASAPORTE'))
            login=self.fake.user_name()
            relacionu=self.fake.word()
            cursor.execute(
                f"INSERT INTO a_usuario id,tipoid,login,relacionu VALUES ({id},'{tipoid}','{login}','{relacionu}')")

    def __populate_a_reserva(self, cursor):
        for i in range(0, 33000):
            id = self.fake.unique.random_int(min=0, max=1000000)
            fecha_inicio = self.fake.date_between(start_date='-10y', end_date='+1y')
            fecha_fin = self.fake.date_between(start_date=fecha_inicio, end_date='+1m')
            personas = self.fake.random_int(min=1, max=10)
            fin_cancelacion_oportuna = self.fake.date_between(start_date='-30d', end_date=fecha_inicio)
            porcentaje_a_pagar = self.fake.random_int(min=0, max=100)
            monto_total = self.fake.random_int(min=100, max=1000)
            propiedad = self.fake.random_int(min=1, max=100)
            colectiva = self.fake.random_int(min=1, max=100)

            cursor.execute(
                f"INSERT INTO a_reserva (id, fecha_inicio, fecha_fin, personas, fin_cancelacion_oportuna," +
                f" porcentaje_a_pagar, monto_total, propiedad, colectiva)"+
                f"VALUES ({id}, '{fecha_inicio}', '{fecha_fin}', {personas}, '{fin_cancelacion_oportuna}'," +
                f"{porcentaje_a_pagar}, {monto_total}, {propiedad}, {colectiva})")

    def __populate_a_oferta(self, cursor):
        for i in range(0, 33000):
            id = self.fake.unique.random_int(min=0, max=1000000)
            capacidad = self.fake.random_int(min=1, max=100)
            precio = self.fake.random_int(min=1, max=1000)
            tamanio = self.fake.random_int(min=1, max=1000)
            dias_reservados = self.fake.random_int(min=1, max=30)
            fecha_creacion = self.fake.date_between(start_date='-1y', end_date='today')
            piso = self.fake.random_int(min=1, max=10)
            habilitada = self.fake.random_int(min=0, max=1)
            operador = self.fake.random_element(elements=('login1', 'login2', 'login3'))

            cursor.execute(
                f"INSERT INTO a_oferta (id, capacidad, precio, tamanio, dias_reservados, fecha_creacion, piso, habilitada, operador) "
                f"VALUES ({id}, {capacidad}, {precio}, {tamanio}, {dias_reservados}, TO_DATE('{fecha_creacion}', 'YYYY-MM-DD'), {piso}, {habilitada}, '{operador}')")
