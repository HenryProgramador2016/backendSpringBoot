package com.example.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "image_table")
public class ImageModel implements Serializable {

	public ImageModel() {

		super();

	}
	public ImageModel(String name,String descripcion,String image) {

		this.name=name;
		this.descripcion=descripcion;
		this.image=image;

	}
	public ImageModel(String name,String descripcion,String urlIMAGE,String tipo) {

		this.name=name;
		this.descripcion=descripcion;
		this.URL=urlIMAGE;
		this.tipo=tipo;

	}

	public ImageModel(String name, String codeImage,String type, byte[] picByte,String descripcion,String URL,String image,String imageBinario,String tipo) {

		this.name = name;
		this.codeImage=codeImage;

		this.type = type;

		this.picByte = picByte;
		this.descripcion=descripcion;
		this.URL=URL;
		this.image=image;
		this.imageBinario=imageBinario;
		this.tipo=tipo;

	}



	@Id

	@Column(name = "id")

	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	
	@Column(name = "codeImage")
	private String codeImage;
		
	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;
	
	@Column(name = "descripcion")
	private String descripcion;
	

	@Column(name = "URL")
	private String URL;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "image",columnDefinition = "LONGTEXT")
	@Lob()
	private String image;
	
	
	@Column(name = "imageBinario",columnDefinition = "LONGTEXT")
	@Lob()
	private String imageBinario;

	// image bytes can have large lengths so we specify a value

	// which is more than the default length for picByte column length = 1000,

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "picByte",length = 1000000000, columnDefinition = "LONGBLOB")
	@Lob()
	private byte[] picByte;

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public String getType() {

		return type;

	}

	public void setType(String type) {

		this.type = type;

	}

	public byte[] getPicByte() {

		return picByte;

	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;

	}

	public String getCodeImage() {
		return codeImage;
	}

	public void setCodeImage(String codeImage) {
		this.codeImage = codeImage;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getImageBinario() {
		return imageBinario;
	}
	public void setImageBinario(String imageBinario) {
		this.imageBinario = imageBinario;
	}

}