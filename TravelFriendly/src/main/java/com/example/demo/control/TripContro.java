package com.example.demo.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import com.example.demo.model.Trip;
import com.example.demo.model.User;
import com.example.demo.repository.TripRepository;
import com.example.demo.repository.UserRepository;

	@Controller 
	public class TripContro implements CommandLineRunner  {

		@Autowired
		private UserRepository userRepo;
		
		
		
		@Autowired
		private TripRepository repository;
		
		@Override
		public void run(String... args) throws Exception {
		
		User u = new User("Matías", "Sarajevo");
		User u2 = new User("Marcos", "Fernandez");
		userRepo.save(u);
		userRepo.save(u2);
	
		Trip t = new Trip("Badajoz", "Madrid","2023-02-17",2,1,"vamonos");
		Trip t2 = new Trip("Montijo", "Caceres","2023-03-19",3,1,"paseito");
		t.SetConductor(u);
		t2.SetConductor(u2);
		
		repository.save(t);
		repository.save(t2);


		}


	
}
