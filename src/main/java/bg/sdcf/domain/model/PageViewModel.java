package bg.sdcf.domain.model;

import bg.sdcf.domain.entities.Section;

public class PageViewModel {

    private String id;
    private String headline;
    private Section section;
    private boolean isPagePublished;
    private String publishedPageId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public boolean isPagePublished() {
        return isPagePublished;
    }

    public void setPagePublished(boolean pagePublished) {
        isPagePublished = pagePublished;
    }

    public String getPublishedPageId() {
        return publishedPageId;
    }

    public void setPublishedPageId(String publishedPageId) {
        this.publishedPageId = publishedPageId;
    }
}
