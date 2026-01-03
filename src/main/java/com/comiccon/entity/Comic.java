package com.comiccon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comic {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String publisher;
	private String author;
	private String description;
	private Double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="genre_id")
	private Genre genre;
	
	//uni directional is preffered  
//	@OneToOne(mappedBy = "comic", cascade = CascadeType.ALL)
//	private Stock stock;
//
//	
//	@OneToMany(mappedBy = "comic", cascade = CascadeType.ALL)
//	private List<Rating> ratings;
	
}
