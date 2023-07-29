package bg.sdcf.repository;

import bg.sdcf.domain.entities.Page;
import bg.sdcf.domain.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PageRepository extends JpaRepository<Page, String> {

    List<Page> findBySection(Section section);
}
