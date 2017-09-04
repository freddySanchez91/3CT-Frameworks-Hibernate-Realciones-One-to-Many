package com.tresct.test;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.management.OperationsException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.tresct.dto.Avaluo;
import com.tresct.dto.DiarioCliente;
import com.tresct.dto.Tramite;
import com.tresct.util.HibernateUtil;

//Esta clase demeustra como realizar operaciones crud en relaciones one to one
public class Test {

	public static void main(String[] args) {

		try {
			configuracion();

		} catch (Exception hbe) {
			System.out.println("HibernateeXCEPTION: " + hbe.getMessage());
		}
	}
	
	public static void configuracion() {
		
		Session sesion = null;
		
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			sesion.beginTransaction();
			
			Timestamp time = new Timestamp( new Date().getTime() );
			Tramite t1 = new Tramite("Ampliacion" , time);
			Tramite t2 = new Tramite("Ampliacion2" , time);
			
			sesion.save(t1);
			sesion.save(t2);
			
			DiarioCliente dc1 = new DiarioCliente("Entrada1" , time);
			DiarioCliente dc2 = new DiarioCliente("Entrada2" , time);
			DiarioCliente dc3 = new DiarioCliente("Entrada3" , time);
			
			//Asiganr a cada diario de cliente un tramite
			dc1.setTramite(t1);
			dc2.setTramite(t1);
			dc3.setTramite(t2);
			
			sesion.save(dc1);
			sesion.save(dc2);
			sesion.save(dc3);	
			
			//Que pasa si quiero asosciar un tramite existente a un nuevo diario de cliente
//			Tramite tx = sesion.load(Tramite.class, 10);
//			DiarioCliente dcx = new DiarioCliente("NuevaEntrada" , time);
//			dcx.setTramite(tx);
//			sesion.save(dcx);
			
		}catch(Exception e) {
			System.out.println("Exception: " + e.getMessage());			
			sesion.getTransaction().rollback();
			sesion.close();
		}finally {
			sesion.close();
		}
	}

}