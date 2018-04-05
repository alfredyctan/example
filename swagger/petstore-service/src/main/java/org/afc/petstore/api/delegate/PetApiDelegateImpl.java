package org.afc.petstore.api.delegate;

import java.util.List;

import org.afc.petstore.api.PetApiDelegate;
import org.afc.petstore.model.ModelApiResponse;
import org.afc.petstore.model.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;



@Component
public class PetApiDelegateImpl implements PetApiDelegate {

	private static final Logger logger = LoggerFactory.getLogger(PetApiDelegateImpl.class);

	@Override
	public ResponseEntity<Void> addPet(Pet body) {
		logger.info("addPet : {}", body);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Void> deletePet(Long petId, String apiKey) {
		logger.info("deletePet : {}", petId, apiKey);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<List<Pet>> findPetsByStatus(List<String> status) {
		logger.info("findPetsByStatus : {}", status);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Pet>> findPetsByTags(List<String> tags) {
		logger.info("findPetsByTags : {}", tags);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Pet> getPetById(Long petId) {
		logger.info("getPetById : {}", petId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Void> updatePet(Pet body) {
		logger.info("updatePet : {}", body);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<Void> updatePetWithForm(Long petId, String name, String status) {
		logger.info("updatePetWithForm : {}, {}, {}", petId, name, status);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@Override
	public ResponseEntity<ModelApiResponse> uploadFile(Long petId, String additionalMetadata, MultipartFile file) {
		logger.info("uploadFile : {}, {}, {}", petId, additionalMetadata, file);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
