package vamigo.vamigoPJ.Service;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import vamigo.vamigoPJ.Es.Indices;
import vamigo.vamigoPJ.Es.Util;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class IndexService {
    private static final Logger LOG = LoggerFactory.getLogger(IndexService.class);
    private static final List<String> INDICES = List.of(Indices.MOVIE_INDEX);
    private final RestHighLevelClient client;

    @Autowired
    public IndexService(RestHighLevelClient client) {
        this.client = client;
    }

    @PostConstruct
    public void tryToCreateIndices() {
        recreateIndices(false);
    }

    public void recreateIndices(final boolean deleteExisting) {
//        final String settings = Util.loadAsString("static/elastic-settings.json");
//
//        final String settings = Util.loadAsString("build/resources/main/static/elastic-settings.json");

        String settings = "";
        ClassPathResource cpr = new ClassPathResource("static/elastic-settings.json");
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            settings = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.warn("IOException", e);
        }

        if (settings == null) {
            LOG.error("Failed to load index settings");
            return;
        }

        for (final String indexName : INDICES) {
            try {
                final boolean indexExists = client
                        .indices()
                        .exists(new GetIndexRequest(indexName), RequestOptions.DEFAULT);
                if (indexExists) {
                    if (!deleteExisting) {
                        continue;
                    }

                    client.indices().delete(
                            new DeleteIndexRequest(indexName),
                            RequestOptions.DEFAULT
                    );
                }
                final CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
                createIndexRequest.settings(settings, XContentType.JSON);

                final String mappings = loadMappings(indexName);
                if (mappings != null) {
                    createIndexRequest.mapping(mappings, XContentType.JSON);
                }

                client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            } catch (final Exception e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    private String loadMappings(String indexName) {
        final String mappings = Util.loadAsString("static/mappings/" + indexName + ".json");
        if (mappings == null) {
            LOG.error("Failed to load mappings for index with name '{}'", indexName);
            return null;
        }

        return mappings;
    }
}

