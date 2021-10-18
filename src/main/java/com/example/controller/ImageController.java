package com.example.controller;



import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.dao.ImageRepository;
import com.example.model.ImageModel;
import com.example.model.ResponseMessage;
import com.example.util.Util;

@RestController
@RequestMapping("/images")
public class ImageController {

	@Autowired(required=true)
	//IProductoService serviceProducto;
	ImageRepository repo;
	//private static Logger LOG=LoggerFactory.getLogger(DemoApplication.class);

	@GetMapping(value="/listado",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageModel> listado() {
	//	LOG.info("Nombre: ");
		System.out.println("se creo la imagen");
		try {
			return new  ResponseEntity<ImageModel>( repo.findByName("pepe"),HttpStatus.OK);
		} catch (Exception e) {
			//LOG.info("Nombre: "+e.getMessage());
			System.out.println(e.getMessage());
			return new  ResponseEntity<ImageModel>( repo.findByName("pepe"),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	/*@PostMapping("/upload")
	public BodyBuilder uplaodImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
		//compressBytes(file.getBytes())
		System.out.println("Original Image Byte Size - " + file.getBytes().length+file.getOriginalFilename());
		ImageModel img = new ImageModel(file.getOriginalFilename(), file.getContentType(),
				file.getBytes());
		//repo.save(img);
		return ResponseEntity.status(HttpStatus.OK);
	}*/
	
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uplaodImage(@RequestParam("imageFile") MultipartFile file,
			@RequestParam("nombre") String nombre,@RequestParam("descripcion") String descripcion,@RequestParam("tipo") String tipo) throws IOException {
			 String util = "";
			 String code="";//"data:image/jpeg;base64,"+r
			 String code64="";
			    try {
			    code=Util.generatCode();
		    	util="http://localhost:9898/images/file/"+code;
		    	code64="data:image/jpeg;base64,"+resolvetoBase64(file);
		    	ImageModel img = new ImageModel(nombre,code, file.getContentType(),file.getBytes(),descripcion,util,resolvetoBase64(file),code64,tipo);
		    	repo.save(img);
		    	System.out.println();;
		    	util = "Uploaded the file successfully: " + file.getOriginalFilename();
		      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(util));
		    } catch (Exception e) {
		    	util = "Could not upload the file: " + file.getOriginalFilename() + "!";
		      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(util));
		    }
	}
	
	
	public String resolvetoBase64( MultipartFile multipartFile ) {
		byte[] encoded;
		String url = null;
		try {
			encoded = Base64Utils.encode(multipartFile.getBytes());
			url=new String(encoded);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	
	}
	
	/* API QUE DECODIFICA LA IMAGEN EN BASE64 A UN ARREGLO DE BYTES, para que el proceso sea mas rapido*/
	  @GetMapping("/file/{id}")
	  public ResponseEntity<byte[]> getFile(@PathVariable String id) throws InterruptedException {
		  
		 //Thread.sleep(5 * 1000);
		  System.out.println("el id es "+id);
		  ImageModel model=repo.findByCodeImage(id);
		  byte[] bytes = Base64.decodeBase64(model.getImage());
		  return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + model.getName() + "\"")
	        .body(bytes);
	  }
	  
	  
	  /* API QUE DEVUELVE  LA IMAGEN EN BYTES DE LA BD*/
	  @GetMapping(path="/verImage/{id}" ,produces = "application/json" )
	  public ResponseEntity<byte[]> getImage(@PathVariable String id) throws InterruptedException {
//		  HashMap<String, String> map = new HashMap<>(); 
//		  System.out.println("el id es "+id);
		  ImageModel model=repo.findByCodeImage(id);
//		  map.put("key",model.getImage());
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + model.getName() + "\"")
	        .body(model.getPicByte());
	  }
	  
	  /*Devuelve las imagegenes en JSON , dentro de ello el esta la imagen codificada en BASE64 , por lo que el proceso es aun mas lento*/
	  @GetMapping("/files/{id}")
	  public ResponseEntity<List<ImageModel>> getListFiles(@PathVariable String id) throws InterruptedException {
		  
	    List<ImageModel> files =repo.listImages(id);
	    System.out.println("El codigo de galleia es"+id);
	   // Thread.sleep(4000);
	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }
	  
	  
	  /*Devuelve las imagegenes en JSON , dentro de ello el esta la imagen que estara accesible mediante la URL  */
	  @GetMapping("/images/{id}")
	  public ResponseEntity<List<ImageModel>> getListImages(@PathVariable String id) {
	    List<ImageModel> files =repo.listImagesMejorado(id);
	    System.out.println("El codigo de galleia es"+id);
	    return ResponseEntity.status(HttpStatus.OK).body(files);
	  }

	  
	
	
	
	

}
