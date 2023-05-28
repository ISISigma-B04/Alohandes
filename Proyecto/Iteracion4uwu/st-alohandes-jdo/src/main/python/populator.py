from datetime import datetime, timedelta
from random import choice, randint
from faker import Faker
from faker.providers import lorem
from faker.providers import company

import json
import cx_Oracle

# noinspection SqlNoDataSourceInspection
class Populator:
    connection_url: str
    connection_driver_name: str
    connection_user_name: str
    connection_password: str
    config_path = ''
    fake = Faker()

    def __init__(self):
        cx_Oracle.init_oracle_client(lib_dir=r"C:\Oracle\instantclient_21_10")
        self.__get_connection()

    def populate_a_usuario(self):
        connection = self.__get_connection()
        cursor = connection.cursor()

        insert_query = "INSERT ALL\n"

        for i in range(0, 200):
            id = self.fake.unique.random_int(min=0, max=1000000)
            tipoid = self.fake.random_choices(elements=('CARNET_U', 'CEDULA', 'PASAPORTE'))[0]
            login = self.fake.unique.user_name()
            relacionu = self.fake.word()

            insert_query += f"INTO a_usuario (id, tipoid, login, relacionu) VALUES ({id}, '{tipoid}', '{login}', '{relacionu}')\n"

        insert_query += "SELECT 1 FROM DUAL"

        cursor.execute(insert_query)
        connection.commit()
        connection.close()

    def populate_a_operador(self):
        connection = self.__get_connection()
        cursor = connection.cursor()

        usuarios_id = cursor.execute("SELECT login FROM a_usuario").fetchall()

        insert_query = "INSERT ALL\n"

        for i in range(0, 200):
            id = choice(usuarios_id)
            numero_rnt = self.fake.unique.random_int(min=0, max=1000000)
            vencimiento_rnt = self.fake.date_between(start_date='-1y', end_date='today').strftime("%Y-%m-%d")
            registro_super_turismo = self.fake.word()
            vencimiento_registro_st = self.fake.date_between(start_date='-1y', end_date='today').strftime("%Y-%m-%d")
            categoria = self.fake.random_element(
                elements=('HOTEL', 'HOSTAL', 'P_NATURAL', 'APARTAMENTO', 'VECINOS', 'VIVIENDA_U'))
            direccion = self.fake.address().replace("'", "''")
            hora_apertura = self.fake.time_object().strftime("%H:%M:%S")
            hora_cierre = self.fake.time_object().strftime("%H:%M:%S")
            tiempo_minimo = self.fake.random_int(min=1, max=24)
            ganancia_anio_actual = self.fake.pydecimal(left_digits=5, right_digits=2, positive=True)
            ganancia_anio_corrido = self.fake.pydecimal(left_digits=5, right_digits=2, positive=True)

            insert_query += f"INTO a_operador (id, numero_rnt, vencimiento_rnt, registro_super_turismo, " \
                            f"vencimiento_registro_st, categoria, direccion, hora_apertura, hora_cierre, tiempo_minimo, " \
                            f"ganancia_anio_actual, ganancia_anio_corrido) " \
                            f"VALUES ({id}, {numero_rnt}, TO_DATE('{vencimiento_rnt}', 'YYYY-MM-DD'), " \
                            f"'{registro_super_turismo}', TO_DATE('{vencimiento_registro_st}', 'YYYY-MM-DD'), " \
                            f"'{categoria}', '{direccion}', TO_TIMESTAMP('{hora_apertura}', 'HH24:MI:SS'), " \
                            f"TO_TIMESTAMP('{hora_cierre}', 'HH24:MI:SS'), {tiempo_minimo}, " \
                            f"{ganancia_anio_actual}, {ganancia_anio_corrido})\n"

        insert_query += "SELECT 1 FROM DUAL"

        cursor.execute(insert_query)
        connection.commit()
        connection.close()

    def populate_a_oferta(self):
        connection = self.__get_connection()
        cursor = connection.cursor()

        usuarios_id = cursor.execute("SELECT login FROM a_usuario").fetchall()

        insert_query = "INSERT ALL\n"

        for i in range(0, 200):
            id = self.fake.unique.random_int(min=0, max=1000000)
            capacidad = self.fake.random_int(min=1, max=100)
            precio = self.fake.random_int(min=1, max=1000)
            tamanio = self.fake.random_int(min=1, max=1000)
            dias_reservados = self.fake.random_int(min=1, max=30)
            fecha_creacion = self.fake.date_between(start_date='-1y', end_date='today').strftime("%Y-%m-%d")
            piso = self.fake.random_int(min=1, max=10)
            habilitada = self.fake.random_int(min=0, max=1)
            operador = choice(usuarios_id)

            insert_query += f"INTO a_oferta (id, capacidad, precio, tamanio, dias_reservados, fecha_creacion, piso, " \
                            f"habilitada, operador) " \
                            f"VALUES ({id}, {capacidad}, {precio}, {tamanio}, {dias_reservados}, " \
                            f"TO_DATE('{fecha_creacion}', 'YYYY-MM-DD'), {piso}, {habilitada}, '{operador}')\n"

        insert_query += "SELECT 1 FROM DUAL"

        cursor.execute(insert_query)
        connection.commit()
        connection.close()

    def populate_a_reservacolectiva(self):
        connection = self.__get_connection()
        cursor = connection.cursor()

        usuarios_id = cursor.execute("SELECT login FROM a_usuario FETCH FIRST 2 ROWS ONLY;").fetchall()

        cursor.execute("INSERT INTO a_cliente (id, mediopago) VALUES (:1, 'TARJETA')", usuarios_id[0])
        cursor.execute("INSERT INTO a_cliente (id, mediopago) VALUES (:1, 'TARJETA')", usuarios_id[1])

        insert_query = "INSERT ALL\n"

        for i in range(0, 200):
            id = self.fake.unique.random_int(min=0, max=100)
            fecha_inicio = self.fake.date_between(start_date='-1y', end_date='+1y').strftime("%Y-%m-%d")
            duracion = self.fake.random_int(min=1, max=30)
            cantidad = self.fake.random_int(min=1, max=10)
            tipo = self.fake.random_element(elements=('APARTAMENTO', 'HABITACION'))
            cliente = choice(usuarios_id)

            insert_query += f"INTO a_reservacolectiva (id, fecha_inicio, duracion, cantidad, tipo, cliente) " \
                            f"VALUES ({id}, TO_DATE('{fecha_inicio}', 'YYYY-MM-DD'), {duracion}, {cantidad}," \
                            f" '{tipo}', {cliente}')\n"

        insert_query += "SELECT 1 FROM DUAL"

        cursor.execute(insert_query)
        connection.commit()
        connection.close()

    def populate_a_reserva(self,):
        connection = self.__get_connection()
        cursor = connection.cursor()

        ofertas_id = cursor.execute("SELECT id FROM a_oferta").fetchall()
        colectivas_id = cursor.execute("SELECT id FROM a_reservacolectiva").fetchall()

        insert_query = "INSERT ALL\n"

        for i in range(0, 200):
            id = self.fake.unique.random_int(min=0, max=1000000)
            fecha_inicio = self.fake.date_between(start_date='-10y', end_date='+1m').strftime("%Y-%m-%d")
            fecha_fin = self.fake.date_between(start_date=fecha_inicio, end_date='+5m').strftime("%Y-%m-%d")
            personas = self.fake.random_int(min=1, max=10)
            fin_cancelacion_oportuna = self.fake.date_between(
                start_date=datetime.strptime(fecha_inicio, "%Y-%m-%d") - timedelta(days=30),
                end_date=datetime.strptime(fecha_inicio, "%Y-%m-%d")).strftime("%Y-%m-%d")
            porcentaje_a_pagar = self.fake.random_int(min=0, max=100)
            monto_total = self.fake.random_int(min=100, max=1000)
            propiedad = choice(ofertas_id)
            colectiva = choice(colectivas_id)

            insert_query += f"INTO a_reserva (id, fecha_inicio, fecha_fin, personas, fin_cancelacion_oportuna," \
                            f" porcentaje_a_pagar, monto_total, propiedad, colectiva) " \
                            f"VALUES ({id}, TO_DATE('{fecha_inicio}', 'YYYY-MM-DD'), TO_DATE('{fecha_fin}', 'YYYY-MM-DD')," \
                            f" {personas}, TO_DATE('{fin_cancelacion_oportuna}', 'YYYY-MM-DD'), {porcentaje_a_pagar}," \
                            f" {monto_total}, {propiedad}, {colectiva}')\n"

        insert_query += "SELECT 1 FROM DUAL"

        cursor.execute(insert_query)
        connection.commit()
        connection.close()

    def __get_connection(self):
        # if needed, place an 'r' before any parameter in order to address special characters such as '\'.
        # For example, if your user name contains '\', you'll need to place 'r' before the user name: user=r'User Name'
        dsn_tns = cx_Oracle.makedsn('fn4.oracle.virtual.uniandes.edu.co', '1521', service_name='prod')
        conn = cx_Oracle.connect(user=r'ISIS2304B28202310', password='LbyCVTywUGkk', dsn=dsn_tns)
        if conn.ping() is None:
            print("Conexion exitosa")
        else:
            print("Conexion fallida")
        return conn


populator = Populator()
populator.populate_a_usuario()
populator.populate_a_operador()
populator.populate_a_oferta()
print("Se pudo mano, a disfrutar")
