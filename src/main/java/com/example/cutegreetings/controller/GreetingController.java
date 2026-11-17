package com.example.cutegreetings.controller;

import com.example.cutegreetings.model.Greeting;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 三个可爱问候接口。
 *
 * <pre>
 *   GET /api/greeting/cat-star    捧着星星的橘白小猫
 *   GET /api/greeting/cat-snow    蓝眼睛的白雪小猫
 *   GET /api/greeting/cat-ginger  奶乎乎的橘色小猫
 * </pre>
 * 每个接口都返回「一张可爱的图片 + 一句可爱的问候」。
 */
@RestController
@RequestMapping("/api")
public class GreetingController {

    private static final String IMAGE_TYPE = MediaType.IMAGE_JPEG_VALUE;

    /** 小猫档案：编号 -> [名字, 图片文件名, 问候语] */
    private static final Map<String, String[]> KITTENS = new LinkedHashMap<>();

    static {
        KITTENS.put("cat-star", new String[]{
                "星星猫",
                "images/cat-star.jpg",
                "抱一颗小星星给你，今天也要闪闪发光呀～"
        });
        KITTENS.put("cat-snow", new String[]{
                "雪球猫",
                "images/cat-snow.jpg",
                "蓝眼睛眨一眨，把一整天的小烦恼都吹跑啦～"
        });
        KITTENS.put("cat-ginger", new String[]{
                "橘子猫",
                "images/cat-ginger.jpg",
                "喵呜～辛苦啦，来蹭一蹭，喝杯热的再继续吧！"
        });
    }

    /** 接口一：捧着星星的小猫。 */
    @GetMapping(value = "/greeting/cat-star", produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting catStar() {
        return buildGreeting("cat-star");
    }

    /** 接口二：蓝眼睛的白雪小猫。 */
    @GetMapping(value = "/greeting/cat-snow", produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting catSnow() {
        return buildGreeting("cat-snow");
    }

    /** 接口三：奶乎乎的橘色小猫。 */
    @GetMapping(value = "/greeting/cat-ginger", produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting catGinger() {
        return buildGreeting("cat-ginger");
    }

    /** 附加接口：随机来一只小猫。 */
    @GetMapping(value = "/greeting/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting random() {
        List<String> ids = List.copyOf(KITTENS.keySet());
        return buildGreeting(ids.get(ThreadLocalRandom.current().nextInt(ids.size())));
    }

    /** 附加接口：一次拿到三只小猫。 */
    @GetMapping(value = "/greetings", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Greeting> all() {
        return KITTENS.keySet().stream().map(this::buildGreeting).toList();
    }

    /** 附加接口：直接返回图片本身（image/jpeg），可以在浏览器里当图片打开。 */
    @GetMapping(value = "/greeting/{id}/image", produces = IMAGE_TYPE)
    public ResponseEntity<byte[]> image(@PathVariable String id) throws IOException {
        String[] kitten = KITTENS.get(id);
        if (kitten == null) {
            return ResponseEntity.notFound().build();
        }
        byte[] bytes = readImage(kitten[1]);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header("Content-Disposition", "inline; filename=\"" + id + ".jpg\"")
                .body(bytes);
    }

    private Greeting buildGreeting(String id) {
        String[] kitten = KITTENS.get(id);
        String name = kitten[0];
        String path = kitten[1];
        String greeting = kitten[2];
        byte[] bytes = readImage(path);
        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/greeting/{id}/image")
                .buildAndExpand(id)
                .toUriString();
        String dataUri = "data:" + IMAGE_TYPE + ";base64," + Base64.getEncoder().encodeToString(bytes);
        return new Greeting(id, name, greeting, imageUrl, dataUri, IMAGE_TYPE);
    }

    private byte[] readImage(String classpathLocation) {
        try {
            return new ClassPathResource("static/" + classpathLocation).getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new UncheckedIOException("读取图片失败: " + classpathLocation, e);
        }
    }
}
