package bg.sdcf.web.controller;

import bg.sdcf.domain.entities.Section;
import bg.sdcf.domain.model.PageDetailsViewModel;
import bg.sdcf.service.PageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends BaseController {

    private final PageService pageService;

    public HomeController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/")
    public ModelAndView homepage() {
        PageDetailsViewModel pageDetails = pageService.getPageBySection(Section.INDEX).get(0);
        pageDetails = !pageDetails.getHeadline().equals("Title") && !pageDetails.getEditordata().equals("Content")
                ? pageDetails
                : null;

        return view("index", "page", pageDetails);
    }

    @GetMapping("/purposes")
    public ModelAndView purposes() {
        return view("pages/purposes", "page", pageService.getPageBySection(Section.PURPOSE).get(0));
    }

    @GetMapping("/publications")
    public ModelAndView publications() {
        return view("pages/publications", "pages", pageService.getPageBySection(Section.PUBLICATIONS));
    }

    @GetMapping("/addictions")
    public ModelAndView addictions() {
        return view("pages/addictions", "page", pageService.getPageBySection(Section.ADDICTIONS).get(0));
    }

    @GetMapping("/family-programs")
    public ModelAndView familyPrograms() {
        return view("pages/family-programs", "page", pageService.getPageBySection(Section.FAMILY_PROGRAMS).get(0));
    }

    @GetMapping("/events")
    public ModelAndView events() {
        return view("pages/events", "pages", pageService.getPageBySection(Section.EVENTS));
    }

}
