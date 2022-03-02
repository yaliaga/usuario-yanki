package pe.com.usuarioyanki.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	
	public String Id;
	public String NameUser;
	public String Document;
	public String NroPhone;
	public String Email;
	public String ImeiPhone;
	
//	private String id;			
//	private String recibir;
//	private String enviar;
}
