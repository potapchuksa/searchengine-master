package searchengine.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "page")
public class PageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false, foreignKey = @ForeignKey(name = "fk_page_site"))
    private SiteEntity site;
    @Column(name = "path", columnDefinition = "TEXT not null, INDEX idx_path (path(50))")
    private String path;
    @Column(name = "code", nullable = false)
    private int code;
    @Column(name = "content", columnDefinition = "MEDIUMTEXT", nullable = false)
    private String content;
}
