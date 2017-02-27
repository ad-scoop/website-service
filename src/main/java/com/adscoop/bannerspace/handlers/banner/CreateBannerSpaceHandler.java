package com.adscoop.bannerspace.handlers.banner;


import com.adscoop.bannerspace.entites.BannerSpace;
import com.adscoop.bannerspace.entites.WebSiteNode;
import com.adscoop.bannerspace.services.WebsiteNodeService;
import com.google.inject.Inject;
import ratpack.handling.Context;
import ratpack.handling.Handler;

import java.util.Optional;

import static ratpack.jackson.Jackson.fromJson;
import static ratpack.jackson.Jackson.json;

/**
 * Created by thokle on 17/10/2016.
 */
public class CreateBannerSpaceHandler implements Handler {

    private WebsiteNodeService websiteNodeService;

    @Inject
    public CreateBannerSpaceHandler(WebsiteNodeService websiteNodeService) {
        this.websiteNodeService = websiteNodeService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
String token = ctx.getRequest().getHeaders().get("token");
String hostname = ctx.getPathTokens().get("hostname");
        if (!token.isEmpty()  && !hostname.isEmpty())  {


            ctx.parse(fromJson(BannerSpace.class)).then(bannerSpace -> {


                Optional<WebSiteNode> webSiteNode = websiteNodeService.findWebSiteByUserTokenAndHostname(token, hostname);

                if(webSiteNode.isPresent()) {
                    BannerSpace bannerSpace1 = new BannerSpace();


                    bannerSpace.getLabels().stream().filter(labelfil -> labelfil != null).forEach(la -> {

                        bannerSpace1.getLabels().add(la);
                    });


                    bannerSpace1.setPosition(bannerSpace.getPosition());
                  bannerSpace1.setHeight(bannerSpace.getHeight());
                    bannerSpace1.setPrice(bannerSpace.getPrice());


                    webSiteNode.get().addBannerSpace(bannerSpace1);




                    websiteNodeService.save(webSiteNode.get());

                    ctx.render(json(bannerSpace1));
                } else {
                    ctx.render(json("no website found"));

                }
            });


        } else {
            ctx.render(json("No token present"));
        }

    }
}
