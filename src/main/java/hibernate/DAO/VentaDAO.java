package hibernate.DAO;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import hibernate.App.HibernateUtil;
import hibernate.dto.PersonaEntity;
import hibernate.dto.VentaEntity;

public class VentaDAO {
	
	public void insertVenta (VentaEntity venta){
	Session session = HibernateUtil.getSessionFactory().openSession();
	session.beginTransaction();
	session.saveOrUpdate(venta);
	session.getTransaction().commit();

	HibernateUtil.shutdown();
}

	@SuppressWarnings("unchecked")
	public List<VentaEntity> getAllVentas() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<VentaEntity> ventas = new ArrayList<VentaEntity>();
		try {
			ventas = session.createQuery("From VentaEntity").list();
			System.out.println("ID_Venta| Fecha| Importe ");
			for (VentaEntity vta : ventas) {
				PersonaEntity per = vta.getPerVtaId();
				per.getPersonaId();
				System.out
						.println(vta.getVentaId() + " " + vta.getFcVenta() + " " + vta.getImporte() );
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		HibernateUtil.shutdown();
		return ventas;
	}

	public void deleteVenta(VentaEntity venta) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		session.delete(venta);
		session.getTransaction().commit();
		HibernateUtil.shutdown();
	}

	
}
