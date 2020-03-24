package hibernate.App;



import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;


import hibernate.DAO.PersonaDAO;
import hibernate.DAO.VentaDAO;
import hibernate.dto.PersonaEntity;
import hibernate.dto.VentaEntity;

public class HibernateAPPmain {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		

		int opcionMenu = mostrarMenu(sc);
		while (opcionMenu != 0) {
			switch (opcionMenu) {
			case 1:
				alta(sc);
				break;
			case 2:
				modificacion(sc);
				break;
			case 3:
				baja(sc);
				break;
			case 4:
				listado(sc, null);
				break;
			case 5:
				busqueda(sc);// con like busqueda por iniciales
				break;
			case 6:
				venta(sc);
			case 7:
				reportes(sc); // venta de cada cliente elegido por el usuario
				break;
			case 0:

				break;

			default:
				System.out.println("Por favor, seleccione una opción correcta.");
				break;
			}
			opcionMenu = mostrarMenu(sc);
		}
	}

	private static void alta(Scanner sc) {
		PersonaEntity per = new PersonaEntity();
		System.out.println("ALTA DE PERSONA");
		System.out.println("Ingrese nombre:");
		String nombre = sc.next();
		per.setNombre(nombre);
		System.out.println("Ingrese fecha nacimiento (aaaa/mm/dd):");
		String fcNac = sc.next();
		per.setFcNac(fcNac);
		PersonaDAO pdao = new PersonaDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		int edad = 0;
		try {
			Date fechaNac = sdf.parse(fcNac);
			edad = pdao.calcularEdad(fechaNac);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		per.setEdad(edad);
		pdao.insertOrUpdatePersona(per);
	}

	private static void reportes(Scanner sc) {
		VentaDAO vDao = new VentaDAO();
		System.out.println("------Reporte de Ventas por Cliente-------");
		System.out.println("Por favor, ingrese el ID del cliente.");
		int clienteId = sc.nextInt();

		PersonaDAO pDao = new PersonaDAO();
		PersonaEntity per = pDao.getPersonaxid(clienteId);

		try {
			System.out.println("Cliente ID: " + clienteId + " Nombre: " + per.getNombre());
			System.out.println(vDao.getAllVentas());
		} catch (HibernateException e) {
			System.out.println("Error al buscar registro");
		}
		HibernateUtil.shutdown();
	}

	private static void busqueda(Scanner sc) {

		System.out.println("------Busqueda de Cliente por Nombre-------");
		System.out.println("Por favor, ingrese el Nombre del Cliente");
		String nombre = sc.next();
		PersonaDAO pDao = new PersonaDAO();
		List<PersonaEntity> personas = pDao.getPersonaxnomb(nombre);

		try {
			System.out.println("ID--NOMBRE----EDAD---F.NACIM---");
		
			for (PersonaEntity persona : personas) {
				System.out.println(persona.getPersonaId() + " " + persona.getNombre() + " " + persona.getEdad() + " "
						+ persona.getFcNac());
			}
		} catch (HibernateException e) {
			System.out.println("Error al buscar registro");
		}
		HibernateUtil.shutdown();
	}

	private static void venta(Scanner sc) {
		VentaEntity vta = new VentaEntity();
		VentaDAO vDao = new VentaDAO();

		Date fechaActual = new Date();
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String fechaVenta = formatoFecha.format(fechaActual);
		vta.setFcVenta(fechaVenta);

		System.out.println("VENTAS");
		System.out.println("Ingrese ID CLIENTE");
		int idCliente = sc.nextInt();
		PersonaDAO pDao = new PersonaDAO();
		PersonaEntity per = pDao.getPersonaxid(idCliente);

		vta.setPerVtaId(per);

		System.out.println("Ingrese monto venta efectuada");
		float importe = sc.nextInt();
		vta.setImporte(importe);

		System.out.println("Desea confirmar la Venta? S/N");
		System.out.println(
				"Cliente ID: " + idCliente + " Nombre: " + per.getNombre() + " Monto Venta: $ " + importe + ".");
		String confirmacion = sc.next();

		if (confirmacion != "N") {
			vDao.insertVenta(vta);
			System.out.println("La venta fue generada con éxito.");

		} else {
			System.out.println("La venta no pudo ser generada.");
		}

		HibernateUtil.shutdown();
	}

	private static void listado(Scanner sc, List<PersonaEntity> personas) {

		try {
			System.out.println("LISTADO--------------------");
			System.out.println("ID--NOMBRE----EDAD---F.NACIM---");
			PersonaDAO pDao = new PersonaDAO();

			for (PersonaEntity persona : pDao.getAllPersonas()) {
				System.out.println(persona.getPersonaId() + " " + persona.getNombre() + " " + persona.getEdad() + " "
						+ persona.getFcNac());
			}

			System.out.println("---------FIN LISTADO------------");
			System.out.println();

		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}

	private static void baja(Scanner sc) {

		System.out.println("BAJA DE PERSONA");
		System.out.println("Ingrese Nro de ID del registro a eliminar");
		int idCliente = sc.nextInt();
		PersonaDAO pDao = new PersonaDAO();
		PersonaEntity per = pDao.getPersonaxid(idCliente);
		System.out.println("¿Desea Eliminar el siguiente registro: ");
		System.out.println(" ID: ");
		System.out.println("1) SI   2) NO");

		int opcionBaja = sc.nextInt();
		if (opcionBaja != 2) {

			try {
				pDao.deletePersona(per);
				System.out.println(
						"El registro Nro. " + idCliente + ": " + per.getNombre() + " ha sido eliminado con éxito.");

			} catch (HibernateException e) {
				e.printStackTrace();

			}
		} else {
			System.out.println("El registro no pudo ser eliminado.");
		}
	}

	private static void modificacion(Scanner sc) {

		System.out.println("MODIFICACION DE DATOS");
		System.out.println("Ingrese Nro de ID del registro a MODIFICAR");
		int idCliente = sc.nextInt();
		PersonaDAO pDao = new PersonaDAO();
		PersonaEntity per = pDao.getPersonaxid(idCliente);

		System.out.println("Por favor, seleccione el dato a modificar: 1) NOMBRE 2)FECHA DE NACIMIENTO");
		int opcionMod = sc.nextInt();
		try {

			switch (opcionMod) {
			case 1:
				System.out.println("Ingrese nuevo Nombre");
				String nombreNew = sc.next();
				per.setNombre(nombreNew);
				pDao.insertOrUpdatePersona(per);
				System.out.println("El nombre del registro " + idCliente + " ha sido modificado con éxito");

				break;
			case 2:

				System.out.println("Ingrese nueva FECHA DE NACIMIENTO (AAAA-MM-DD)");
				String fNacNew = sc.next();
				per.setFcNac(fNacNew);
				;
				pDao.insertOrUpdatePersona(per);
				;
				System.out
						.println("La fecha de Nacimiento del registro " + idCliente + " ha sido modificada con éxito");
				break;
			default:
				break;
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		}
	}

	private static int mostrarMenu(Scanner sc) {

		System.out.println("SISTEMA DE PERSONAS (ABM)");
		System.out.println("=========================");
		System.out.println("MENU OPCIONES: ");
		System.out.println("1: ALTA ");
		System.out.println("2: MODIFICACION ");
		System.out.println("3: BAJA");
		System.out.println("4: LISTADO");
		System.out.println("5: BUSQUEDA");
		System.out.println("6: VENTAS");
		System.out.println("7: REPORTE");
		System.out.println("0: SALIR");
		int opcion = 0;
		opcion = sc.nextInt();
		return opcion;
	}

}
