import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Basedatos {
	private static Connection conn;

	public static void main(String[] args) {
		String consulta="";
		try {
			iniciarDB();
			// imprimirTabla("entrenadores");
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			 consulta = JOptionPane.showInputDialog("Introduce la consulta a lanzar");
			lanzarConsulta(consulta);
		} catch (Exception e) {
			System.out.println("La consulta " + consulta +" no es correcta");
			System.out.println(e.getLocalizedMessage());
		}

	}

	public static void iniciarDB() throws ClassNotFoundException, SQLException {
		// Esto carga el driver de mysql
		Class.forName("com.mysql.jdbc.Driver");
		// Configuramos la conexion
		conn = DriverManager.getConnection("jdbc:mysql://localhost/mydb?" + "user=root&password=SQL852147852");

	}

	public static void imprimirTabla(String nombretabla) {
		Statement peticion = abrirPeticion();
		// Stamtements permite lanzar peticiones (Query) a la base de datos

		try {
			ResultSet rec = peticion.executeQuery("select * " + "from " + nombretabla);

			int ncolumnas = rec.getMetaData().getColumnCount();
			// Filas
			while (rec.next()) {
				// Columnas
				for (int i = 1; i <= ncolumnas; i++) {
					System.out.println(rec.getMetaData().getColumnName(i) + " : " + rec.getString(i));
					// JOptionPane.showMessageDialog(null, rec.getMetaData().getColumnName(i) + " :
					// " + rec.getString(i));

				}
			}
		} catch (SQLException ex) {

		}
		cerrarPeticion(peticion);
		

	}
/**
 * Lanza la peticion a la base de datos
 * @param consulta consulta a lanzar
 * @throws SQLException excepcion en caso de error de la consulta
 */
	public static void lanzarConsulta(String consulta) throws SQLException {
		Statement peticion = abrirPeticion();

		ResultSet rec = peticion.executeQuery(consulta);

		int ncolumnas = rec.getMetaData().getColumnCount();
		// Filas
		while (rec.next()) {
			// Columnas
			for (int i = 1; i <= ncolumnas; i++) {
				System.out.println(rec.getMetaData().getColumnName(i) + " : " + rec.getString(i));
				// JOptionPane.showMessageDialog(null, rec.getMetaData().getColumnName(i) + " :
				// " + rec.getString(i));

			}
			System.out.println("");
		}

		cerrarPeticion(peticion);

	}

	public static Statement abrirPeticion() {
		try {
			return conn.createStatement();
			// crea la peticion
		} catch (SQLException ex) {
			// En caso de error lo imprimimos
			System.out.println(ex.getLocalizedMessage());
		}
		return null;
	}

	public static void cerrarPeticion(Statement peticion) {
		try {
			peticion.close();
		} catch (SQLException ex) {
			// En caso de error lo imprimimos
			System.out.println(ex.getLocalizedMessage());
		}
	}

}
