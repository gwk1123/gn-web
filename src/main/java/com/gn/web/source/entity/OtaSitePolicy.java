package com.gn.web.source.entity;
import lombok.Data;
import java.util.List;

@Data
public class OtaSitePolicy {

    private List<SourceData> siteRoutings;

    private SiteSearchRequest siteSearchRequest;

    private OtaRequest otaRequest;

}
