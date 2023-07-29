package bg.sdcf.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "page")
public class Page extends BaseEntity {

   private String description;
   private String headline;
   private String editordata;
   private boolean isPagePublished;
   private Section section;
   private String publishedPageId;


   @Column(name = "description")
   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   @Column(name = "headline")
   public String getHeadline() {
      return headline;
   }

   public void setHeadline(String headline) {
      this.headline = headline;
   }

   @Column(columnDefinition = "text")
   public String getEditordata() {
      return editordata;
   }

   public void setEditordata(String editordata) {
      this.editordata = editordata;
   }

   @Column(name = "published")
   public boolean isPagePublished() {
      return isPagePublished;
   }

   public void setPagePublished(boolean pagePublished) {
      isPagePublished = pagePublished;
   }

   @Column(name = "section")
   public Section getSection() {
      return section;
   }

   public void setSection(Section section) {
      this.section = section;
   }

   @Column(name = "published_page_id", nullable = true)
   public String getPublishedPageId() {
      return publishedPageId;
   }

   public void setPublishedPageId(String publishedPageId) {
      this.publishedPageId = publishedPageId;
   }
}
