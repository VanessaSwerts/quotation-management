package br.inatel.icc.quotationmanagement.controller;

import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/stockcache")
@Slf4j
public class CacheController {

	@DeleteMapping
	@Transactional
	@CacheEvict(value = { "allStocks", "stockById" }, allEntries = true)
	public ResponseEntity<?> cleanCache() {
		log.info("Cleaning cache!");
		return ResponseEntity.status(204).body("Cache cleaned!");
	}

}
