package com.gestion.filmotheque;

import com.gestion.filmotheque.entities.Acteur;
import com.gestion.filmotheque.entities.Categorie;
import com.gestion.filmotheque.entities.Film;
import com.gestion.filmotheque.repository.ActeurRepository;
import com.gestion.filmotheque.repository.CategorieRepository;
import com.gestion.filmotheque.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProjetD1Application {

    public static void main(String[] args) {
        SpringApplication.run(ProjetD1Application.class, args);
    }

    @Bean
    public CommandLineRunner initialData(
            @Autowired CategorieRepository categorieRepository,
            @Autowired ActeurRepository acteurRepository,
            @Autowired FilmRepository filmRepository) {
        return args -> {
            // Create uploads directory if it doesn't exist
            Path uploadsDir = Paths.get("uploads");
            if (!Files.exists(uploadsDir)) {
                Files.createDirectories(uploadsDir);
                System.out.println("Created uploads directory");
            }

            // Check if data already exists
            if (categorieRepository.count() > 0) {
                System.out.println("Database already initialized, skipping data initialization");
                return;
            }

            System.out.println("Initializing sample data...");

            // Create categories
            Categorie action = new Categorie(0, "Action", null);
            Categorie comedy = new Categorie(0, "Comédie", null);
            Categorie drama = new Categorie(0, "Drame", null);
            Categorie sciFi = new Categorie(0, "Science Fiction", null);

            categorieRepository.saveAll(Arrays.asList(action, comedy, drama, sciFi));

            // Create actors
            Acteur actor1 = new Acteur(0, "Dicaprio", "Leonardo", null);
            Acteur actor2 = new Acteur(0, "Smith", "Will", null);
            Acteur actor3 = new Acteur(0, "Johansson", "Scarlett", null);
            Acteur actor4 = new Acteur(0, "Pitt", "Brad", null);

            acteurRepository.saveAll(Arrays.asList(actor1, actor2, actor3, actor4));

            // Create sample images in the uploads directory
            createSampleImage("sample_inception.jpg", "static/images/sample_inception.jpg");
            createSampleImage("sample_wolf.jpg", "static/images/sample_wolf.jpg");
            createSampleImage("sample_mib.jpg", "static/images/sample_mib.jpg");
            createSampleImage("sample_fightclub.jpg", "static/images/sample_fightclub.jpg");

            // Create films
            Film film1 = new Film();
            film1.setTitre("Inception");
            film1.setDescription("Un voleur qui s'infiltre dans les rêves des autres pour y voler des idées.");
            film1.setAnneeparution(2010);
            film1.setCategorie(sciFi);
            film1.setImagePath("sample_inception.jpg");
            film1.setActeurs(Arrays.asList(actor1, actor3));

            Film film2 = new Film();
            film2.setTitre("Le Loup de Wall Street");
            film2.setDescription("L'histoire vraie de Jordan Belfort, un courtier en bourse qui a fait fortune.");
            film2.setAnneeparution(2013);
            film2.setCategorie(drama);
            film2.setImagePath("sample_wolf.jpg");
            film2.setActeurs(List.of(actor1));

            Film film3 = new Film();
            film3.setTitre("Men in Black");
            film3.setDescription("Des agents secrets qui surveillent les activités extraterrestres sur Terre.");
            film3.setAnneeparution(1997);
            film3.setCategorie(sciFi);
            film3.setImagePath("sample_mib.jpg");
            film3.setActeurs(List.of(actor2));

            Film film4 = new Film();
            film4.setTitre("Fight Club");
            film4.setDescription(
                    "Un employé de bureau insomniaque et un vendeur de savon charismatique forment un club de combat clandestin.");
            film4.setAnneeparution(1999);
            film4.setCategorie(drama);
            film4.setImagePath("sample_fightclub.jpg");
            film4.setActeurs(List.of(actor4));

            filmRepository.saveAll(Arrays.asList(film1, film2, film3, film4));

            System.out.println("Sample data initialization complete");
        };
    }

    private void createSampleImage(String targetFilename, String resourcePath) {
        try {
            // Check if the sample image already exists
            Path targetPath = Paths.get("uploads", targetFilename);
            if (Files.exists(targetPath)) {
                System.out.println("Sample image " + targetFilename + " already exists, skipping");
                return;
            }

            // First try to load from classpath resources
            try {
                Resource resource = new ClassPathResource(resourcePath);
                if (resource.exists()) {
                    try (InputStream is = resource.getInputStream()) {
                        Files.copy(is, targetPath);
                        System.out.println("Created sample image: " + targetFilename);
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Could not load image from classpath: " + e.getMessage());
            }

            // If resource doesn't exist, create a placeholder image with a color
            System.out.println("Resource " + resourcePath + " not found, creating placeholder image");
            createPlaceholderImage(targetPath);

        } catch (IOException e) {
            System.err.println("Error creating sample image " + targetFilename + ": " + e.getMessage());
        }
    }

    private void createPlaceholderImage(Path targetPath) throws IOException {
        // Create a simple text file as a placeholder
        // In a real app, you would generate an actual image using libraries like Java2D
        String placeholderContent = "This is a placeholder for an image file.";
        Files.writeString(targetPath, placeholderContent);
        System.out.println("Created placeholder at: " + targetPath);
    }
}
