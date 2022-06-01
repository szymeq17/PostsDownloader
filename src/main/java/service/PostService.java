package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import model.Post;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class PostService {

    private static final String RESOURCE_URL = "https://jsonplaceholder.typicode.com/posts";
    private static final String POSTS_PATH = "posts";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void downloadAndSavePostsToJsonFiles() {
        List<Post> posts = retrievePosts();

        // create directory if does not exist
        File directory = new File(POSTS_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        posts.forEach(this::savePostAsJson);
    }

    @SneakyThrows
    private List<Post> retrievePosts() {
        URL url = new URL(RESOURCE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("accept", "application/json");

        InputStream responseStream = connection.getInputStream();

        Post[] posts = objectMapper.readValue(responseStream, Post[].class);

        return Arrays.asList(posts);
    }

    @SneakyThrows
    private void savePostAsJson(Post post) {
        String path = createPostPath(post.id());
        objectMapper.writeValue(new File(path), post);
    }

    private String createPostPath(Long postId) {
        return String.format("%s/%s.json", POSTS_PATH, postId.toString());
    }
}
