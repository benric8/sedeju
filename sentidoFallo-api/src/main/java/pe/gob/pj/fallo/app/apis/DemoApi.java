package pe.gob.pj.fallo.app.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoApi {

	@RequestMapping(value = "demo", method = RequestMethod.GET)
	public ResponseEntity<String> prueba() {
		return new ResponseEntity<>("Hola como estas", HttpStatus.OK);
	}
}
