package hibernate.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


	
	@Entity
	@Table(name = "Persona", uniqueConstraints = {
			@UniqueConstraint(columnNames = "ID"),
			})

	public class PersonaEntity implements Serializable {
		
		private static final long serialVersionUID = -1798070786993154676L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "ID", unique = true, nullable = false)
		private Integer personaId;
		
		@Column(name = "NOMBRE",unique = true, nullable = false, length = 50)
		private String nombre;
		
		@Column(name = "EDAD",unique = true, nullable = false, length = 3)
		private int edad;
		
		@Column(name = "FECHA_NACIMIENTO", unique = true, length = 100)
		private String fcNac;


		public Integer getPersonaId() {
			return personaId;
		}

		public void setPersonaId(Integer personaId) {
			this.personaId = personaId;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public int getEdad() {
			return edad;
		}

		public void setEdad(int edad) {
			this.edad = edad;
		}

		public String getFcNac() {
			return fcNac;
		}

		public void setFcNac(String fcNac) {
			this.fcNac = fcNac;
		
		}


}
