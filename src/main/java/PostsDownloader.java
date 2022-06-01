import service.PostService;

public class PostsDownloader {

    public static void main(String[] args) {
        PostService postService = new PostService();
        postService.downloadAndSavePostsToJsonFiles();
    }
}
