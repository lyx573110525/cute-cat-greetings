package com.example.cutegreetings.model;

/**
 * 一个可爱的问候。
 *
 * @param id         小猫的编号，如 cat-star
 * @param name       小猫的名字
 * @param greeting   一句可爱的问候
 * @param imageUrl   图片地址（可直接在浏览器打开）
 * @param imageBase64 图片内容本身（data URI），方便前端一行代码直接渲染
 * @param imageType  图片 MIME 类型
 */
public record Greeting(
        String id,
        String name,
        String greeting,
        String imageUrl,
        String imageBase64,
        String imageType
) {
}
