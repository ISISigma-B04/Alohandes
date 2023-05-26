const faker = require('faker');
const oracledb = require('oracledb');
const fs = require('fs');

const dbConfig = JSON.parse(fs.readFileSync('config.json', 'utf8'));

async function populateDatabase() {
  let connection;

  try {
    connection = await oracledb.getConnection(dbConfig);
//TODO cambiar esto para la respectiva llenada correcta
    const name = faker.name.firstName();
    const phone = faker.phone.phoneNumber();

    // Crear una sentencia INSERT para insertar datos en la tabla
    const insertQuery = `INSERT INTO tu_tabla (nombre, telefono) VALUES (:name, :phone)`;

    await connection.execute(insertQuery, { name, phone });

  } catch (error) {
    console.error('Error al poblar la base de datos:', error);

  } finally {
    if (connection) {
      // Liberar la conexión
      await connection.close();
    }
  }
}

populateDatabase();
