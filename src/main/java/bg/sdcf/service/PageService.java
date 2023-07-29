package bg.sdcf.service;

import bg.sdcf.domain.entities.Section;
import bg.sdcf.domain.model.PageDetailsViewModel;
import bg.sdcf.domain.model.PageForm;
import bg.sdcf.domain.model.PageViewModel;

import java.util.List;

public interface PageService {

    List<PageViewModel> getAllPages();

    void createPage(PageForm pageForm);

    PageForm getPageById(String id);

    void editPage(PageForm pageForm);

    List<PageDetailsViewModel> getPageBySection(Section section);
}
