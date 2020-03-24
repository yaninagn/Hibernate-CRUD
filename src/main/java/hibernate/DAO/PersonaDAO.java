package hibernate.DAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import hibernate.App.HibernateUtil;
import hibernate.dto.PersonaEntity;

public class PersonaDAO {

	public static void insertOrUpdatePersona(PersonaEntity per) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.saveOrUpdate(per);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	@SuppressWarnings("unchecked")
	public List<PersonaEntity> getAllPersonas() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<PersonaEntity> listaPersonas = new ArrayList<PersonaEntity>();
		listaPersonas = session.createQuery("From PersonaEntity").list();
		HibernateUtil.shutdown();
		return listaPersonas;

	}

	@SuppressWarnings("unchecked")
	public List<PersonaEntity> getPersonaxnomb(String nombre) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<PersonaEntity> perso1 = session.createQuery("From PersonaEntity where nombre ='" + nombre + "'").list();
		HibernateUtil.shutdown();
		return perso1;
	}

	public PersonaEntity getPersonaxid(long personaId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session.createQuery("From PersonaEntity where personaId = " + personaId);
		PersonaEntity persona = (PersonaEntity) query.uniqueResult();
		HibernateUtil.shutdown();
		return persona;
	}

	public static void deletePersona(PersonaEntity persona) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(persona);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	public int calcularEdad(Date fechaNac) {
		GregorianCalendar gc = new GregorianCalendar();
		GregorianCalendar hoy = new GregorianCalendar();
		gc.setTime(fechaNac);
		int anioActual = hoy.get(Calendar.YEAR);
		int anioNacim = gc.get(Calendar.YEAR);

		int mesActual = hoy.get(Calendar.MONTH);
		int mesNacim = gc.get(Calendar.MONTH);

		int diaActual = hoy.get(Calendar.DATE);
		int diaNacim = gc.get(Calendar.DATE);

		int dif = anioActual - anioNacim;

		if (mesActual < mesNacim) {
			dif = dif - 1;
		} else {
			if (mesActual == mesNacim && diaActual < diaNacim) {
				dif = dif - 1;
			}
		}

		return dif;

	}
}