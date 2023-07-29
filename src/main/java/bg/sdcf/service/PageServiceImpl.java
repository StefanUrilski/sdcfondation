package bg.sdcf.service;

import bg.sdcf.domain.entities.Page;
import bg.sdcf.domain.entities.Section;
import bg.sdcf.domain.model.PageDetailsViewModel;
import bg.sdcf.domain.model.PageViewModel;
import bg.sdcf.repository.PageRepository;
import bg.sdcf.domain.model.PageForm;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PageServiceImpl implements PageService {

    private static final String PUBLISH = "publish";

    private final PageRepository pageRepository;
    private final ModelMapper modelMapper;

    public PageServiceImpl(PageRepository pageRepository,
                           ModelMapper modelMapper) {
        this.pageRepository = pageRepository;
        this.modelMapper = modelMapper;
    }

    @PostConstruct
    private void init() {
        if (!pageRepository.findAll().isEmpty()) {
            return;
        }

        List<Page> pages = new ArrayList<>();
        for (Section section : Section.values()) {
            if (section == Section.PUBLICATIONS ||section == Section.EVENTS) {
                continue;
            }
            Page page = new Page();
            page.setHeadline("Title");
            page.setEditordata("Content");
            page.setPagePublished(true);
            page.setSection(section);
            pages.add(page);
        }
        pageRepository.saveAll(pages);
    }

    @Override
    public List<PageViewModel> getAllPages() {
        return List.of(modelMapper.map(
                pageRepository.findAll(),
                PageViewModel[].class
        ));
    }

    @Override
    public void createPage(PageForm pageForm) {
        Page page = modelMapper.map(pageForm, Page.class);
        page.setPagePublished(pageForm.getAction().equals(PUBLISH));
        page.setEditordata(new String(page.getEditordata()));
        pageRepository.save(page);
    }

    @Override
    public PageForm getPageById(String id) {
        return modelMapper.map(
                pageRepository.findById(id).orElse(new Page()),
                PageForm.class
        );
    }

    @Override
    @Transactional
    public void editPage(PageForm pageForm) {
        Optional<Page> pageOptional = pageRepository.findById(pageForm.getId());
        if (pageOptional.isEmpty()) {
            System.out.println("ERROR: NO SUCH PAGE");
            this.createPage(pageForm);
            return;
        }
        Page page = pageOptional.get();
        if (pageForm.getAction().equals(PUBLISH) && page.getPublishedPageId() == null) {
            this.createPage(pageForm);
            return;
        }

        Optional<Page> publishedPage = pageRepository.findById(page.getPublishedPageId());
        if (publishedPage.isPresent()) {
            if (pageForm.getAction().equals(PUBLISH)) {
                pageRepository.delete(publishedPage.get());
                pageForm.setPublishedPageId(null);
            } else {
                pageForm.setPublishedPageId(page.getPublishedPageId());
            }
        }
        this.createPage(pageForm);
    }

    @Override
    public List<PageDetailsViewModel> getPageBySection(Section section) {
        return List.of(modelMapper.map(
                pageRepository.findBySection(section),
                PageDetailsViewModel[].class
        ));
    }
}
