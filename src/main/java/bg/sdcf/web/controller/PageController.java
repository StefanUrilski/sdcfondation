package bg.sdcf.web.controller;

import bg.sdcf.domain.model.PageForm;
import bg.sdcf.service.PageService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@PreAuthorize("hasRole('ROLE_MODERATOR')")
@RequestMapping("/page")
public class PageController extends BaseController {

   private final PageService pageService;

   public PageController(PageService pageService) {
      this.pageService = pageService;
   }

   @GetMapping(value = "/all-pages")
   public ModelAndView createPage() {
      return view("pages/all-pages", "pages", pageService.getAllPages());
   }

   @GetMapping(value = "/{id}")
   public ModelAndView article(@PathVariable String id) {
      return view("pages/article", "page", pageService.getPageById(id));
   }

   @GetMapping(value = "/create-page")
   public ModelAndView createPage(@ModelAttribute final PageForm page) {
      return view("pages/create-page", "page", page);
   }

   @PostMapping(value = "/create-page")
   public ModelAndView addPage(@Valid @ModelAttribute final PageForm page, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return view("pages/create-page", "page", page);
      }
      pageService.createPage(page);

      return view("pages/page-success", "action", page.getAction());
   }

   @GetMapping(value = "/edit-page/{id}")
   public ModelAndView editPage(@PathVariable String id) {
      return view("pages/edit-page", "page", pageService.getPageById(id));
   }

   @PostMapping(value = "/edit-page")
   public ModelAndView editPage(@Valid @ModelAttribute final PageForm page, BindingResult bindingResult) {
      if (bindingResult.hasErrors()) {
         return view("pages/edit-page", "page", page);
      }
      pageService.editPage(page);

      return view("pages/page-success", "action", page.getAction());
   }

}
