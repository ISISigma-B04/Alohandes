from datetime import datetime, timedelta
from random import choice
from faker import Faker

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

    def __get_connection(self):
        dsn_tns = cx_Oracle.makedsn('fn4.oracle.virtual.uniandes.edu.co', '1521', service_name='prod')
        conn = cx_Oracle.connect(user=r'ISIS2304B28202310', password='LbyCVTywUGkk', dsn=dsn_tns)
        if conn.ping() is None:
            print("Conexion exitosa")
        else:
            print("Conexion fallida")
        return conn

    def populate_a_usuario(self):
        with self.__get_connection().cursor() as cursor:
            insert_query = "INSERT INTO a_usuario (id, tipoid, login, relacionu) VALUES (:id, :tipoid, :login, :relacionu)"
            data = []

            for _ in range(200):
                data.append({
                    'id': self.fake.unique.random_int(min=0, max=1000000),
                    'tipoid': self.fake.random_choices(elements=('CARNET_U', 'CEDULA', 'PASAPORTE'))[0],
                    'login': self.fake.unique.user_name(),
                    'relacionu': self.fake.word()
                })
                print("Llevamos " + str(_) + " usuarios")

            cursor.executemany(insert_query, data)

    def populate_a_operador(self):
        with self.__get_connection().cursor() as cursor:
            usuarios_id = cursor.execute("SELECT login FROM a_usuario").fetchall()
            insert_query = "INSERT INTO a_operador (id, numero_rnt, vencimiento_rnt, registro_super_turismo, " \
                           "vencimiento_registro_st, categoria, direccion, hora_apertura, hora_cierre, tiempo_minimo, " \
                           "ganancia_anio_actual, ganancia_anio_corrido) " \
                           "VALUES (:id, :numero_rnt, TO_DATE(:vencimiento_rnt, 'YYYY-MM-DD'), :registro_super_turismo, " \
                           "TO_DATE(:vencimiento_registro_st, 'YYYY-MM-DD'), :categoria, :direccion, " \
                           "TO_TIMESTAMP(:hora_apertura, 'HH24:MI:SS'), TO_TIMESTAMP(:hora_cierre, 'HH24:MI:SS'), " \
                           ":tiempo_minimo, :ganancia_anio_actual, :ganancia_anio_corrido)"

            data = []

            for _ in range(200):
                data.append({
                    'id': choice(usuarios_id),
                    'numero_rnt': self.fake.unique.random_int(min=0, max=1000000),
                    'vencimiento_rnt':
                        self.fake.date_between(start_date='-1y', end_date='today').strftime("%Y-%m-%d"),
                    'registro_super_turismo': self.fake.word(),
                    'vencimiento_registro_st': self.fake.date_between(start_date='-1y', end_date='today').strftime(
                        "%Y-%m-%d"),
                    'categoria': self.fake.random_element(
                        elements=('HOTEL', 'HOSTAL', 'P_NATURAL', 'APARTAMENTO', 'VECINOS', 'VIVIENDA_U')),
                    'direccion': self.fake.address().replace("'", "''"),
                    'hora_apertura': self.fake.time_object().strftime("%H:%M:%S"),
                    'hora_cierre': self.fake.time_object().strftime("%H:%M:%S"),
                    'tiempo_minimo': self.fake.random_int(min=1, max=24),
                    'ganancia_anio_actual': self.fake.pydecimal(left_digits=5, right_digits=2, positive=True),
                    'ganancia_anio_corrido': self.fake.pydecimal(left_digits=5, right_digits=2, positive=True)
                })
                print("Llevamos " + str(_) + " operadores")

            cursor.executemany(insert_query, data)

    def populate_a_oferta(self):
        with self.__get_connection().cursor() as cursor:
            usuarios_id = cursor.execute("SELECT login FROM a_usuario").fetchall()

            insert_query = "INSERT INTO a_oferta (id, capacidad, precio, tamanio, dias_reservados, fecha_creacion, piso, " \
                           "habilitada, operador) " \
                           "VALUES (:id, :capacidad, :precio, :tamanio, :dias_reservados, TO_DATE(:fecha_creacion, 'YYYY-MM-DD'), " \
                           ":piso, :habilitada, :operador)"

            data = []

            for _ in range(200):
                data.append({
                    'id': self.fake.unique.random_int(min=0, max=1000000),
                    'capacidad': self.fake.random_int(min=1, max=100),
                    'precio': self.fake.random_int(min=1, max=1000),
                    'tamanio': self.fake.random_int(min=1, max=1000),
                    'dias_reservados': self.fake.random_int(min=1, max=30),
                    'fecha_creacion': self.fake.date_between(start_date='-1y', end_date='today').strftime("%Y-%m-%d"),
                    'piso': self.fake.random_int(min=1, max=10),
                    'habilitada': self.fake.random_int(min=0, max=1),
                    'operador': choice(usuarios_id)
                })
                print("Llevamos " + str(_) + " ofertas")

            cursor.executemany(insert_query, data)

    def populate_a_reservacolectiva(self):
        with self.__get_connection().cursor() as cursor:
            usuarios_id = cursor.execute("SELECT login FROM a_usuario FETCH FIRST 2 ROWS ONLY").fetchall()

            cursor.execute("INSERT INTO a_cliente (id, mediopago) VALUES (:id, 'TARJETA')", {'id': usuarios_id[0][0]})
            cursor.execute("INSERT INTO a_cliente (id, mediopago) VALUES (:id, 'TARJETA')", {'id': usuarios_id[1][0]})

            insert_query = "INSERT INTO a_reservacolectiva (id, fecha_inicio, duracion, cantidad, tipo, cliente) " \
                           "VALUES (:id, TO_DATE(:fecha_inicio, 'YYYY-MM-DD'), :duracion, :cantidad, :tipo, :cliente)"

            data = []

            for _ in range(200):
                data.append({
                    'id': self.fake.unique.random_int(min=0, max=100),
                    'fecha_inicio': self.fake.date_between(start_date='-1y', end_date='+1y').strftime("%Y-%m-%d"),
                    'duracion': self.fake.random_int(min=1, max=30),
                    'cantidad': self.fake.random_int(min=1, max=10),
                    'tipo': self.fake.random_element(elements=('APARTAMENTO', 'HABITACION')),
                    'cliente': choice(usuarios_id)[0]
                })
                print("Llevamos " + str(_) + " reservas colectivas")

            cursor.executemany(insert_query, data)

    def populate_a_reserva(self):
        with self.__get_connection().cursor() as cursor:
            ofertas_id = cursor.execute("SELECT id FROM a_oferta").fetchall()
            colectivas_id = cursor.execute("SELECT id FROM a_reservacolectiva").fetchall()

            insert_query = "INSERT INTO a_reserva (id, fecha_inicio, fecha_fin, personas, fin_cancelacion_oportuna, " \
                           "porcentaje_a_pagar, oferta, colectiva) " \
                           "VALUES (:id, TO_DATE(:fecha_inicio, 'YYYY-MM-DD'), TO_DATE(:fecha_fin, 'YYYY-MM-DD'), " \
                           ":personas, TO_DATE(:fin_cancelacion_oportuna, 'YYYY-MM-DD'), :porcentaje_a_pagar, :oferta," \
                           " :colectiva)"
            data = []

            for _ in range(200):
                fecha_inicio = self.fake.date_between(start_date='-10y', end_date='+1m').strftime("%Y-%m-%d")
                data.append({
                    'id': self.fake.unique.random_int(min=0, max=1000000),
                    'fecha_inicio': fecha_inicio,
                    'fecha_fin': self.fake.date_between(start_date=fecha_inicio, end_date='+5m').strftime("%Y-%m-%d"),
                    'personas': self.fake.random_int(min=1, max=10),
                    'fin_cancelacion_oportuna': (
                                datetime.strptime(fecha_inicio, "%Y-%m-%d") - timedelta(days=30)).strftime("%Y-%m-%d"),
                    'porcentaje_a_pagar': self.fake.random_int(min=0, max=100),
                    'monto_total': self.fake.random_int(min=100, max=1000),
                    'propiedad': choice(ofertas_id)[0],
                    'colectiva': choice(colectivas_id)[0]
                })
                print("Llevamos " + str(_) + " reservas")

            cursor.executemany(insert_query, data)


populator = Populator()
populator.populate_a_usuario()
populator.populate_a_operador()
populator.populate_a_oferta()
populator.populate_a_reservacolectiva()
populator.populate_a_reserva()
print("Se pudo mano, a disfrutar")
