package me.marcsteiner.glacier;

import me.marcsteiner.glacier.routes.blog.BlogRouter;
import org.kohsuke.MetaInfServices;
import ro.pippo.core.Application;
import ro.pippo.core.route.*;
import ro.pippo.freemarker.FreemarkerTemplateEngine;

@MetaInfServices
public class GlacierWeb extends Application {

    @Override
    protected void onInit() {
        setTemplateEngine(new FreemarkerTemplateEngine());

        setUploadLocation(Glacier.getInstance().getConfig().getString("upload.location"));

        // Add routes for static content
        addResourceRoute(new PublicResourceHandler());
        addResourceRoute(new WebjarsResourceHandler());
        addFileResourceRoute("/static", "static");

        getRouter().ignorePaths("/favicon.ico");

        addBeforeFilters();

        // Groups
        addRouteGroup(new BlogRouter());

        GET("/", routeContext -> routeContext.redirect("/blog"));

        addAfterFilters();
    }

    @Override
    protected void onDestroy() {

    }

    private void addBeforeFilters() {
        ANY("/.*", new CSRFHandler());
    }

    private void addAfterFilters() {

    }

}
