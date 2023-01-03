package am.solido.restexample.repository;

import am.solido.restexample.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {


}
