package bg.sdcf.domain.model;

import bg.sdcf.domain.entities.Section;

public class PageDetailsViewModel {

    private String id;
    private String headline;
    private String description;
    private String editordata;
    private Section section;
    private String action;
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


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEditordata() {
        return editordata;
    }

    public void setEditordata(String editordata) {
        this.editordata = editordata;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPublishedPageId() {
        return publishedPageId;
    }

    public void setPublishedPageId(String publishedPageId) {
        this.publishedPageId = publishedPageId;
    }
}
