package com.github.pure.cm.base.web.component.conver;

import com.github.pure.cm.base.web.RequestHelper;
import com.github.pure.cm.base.web.consant.ReqEntrance;
import com.github.pure.cm.common.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * <p>
 * 统一返回格式转换器
 *
 * </p>
 *
 * @author : 陈欢
 * @date : 2020-07-31 16:46
 **/
@Slf4j
@Configuration
public class JsonMessageConver extends MappingJackson2HttpMessageConverter {

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        if (ReqEntrance.gateway.eq(RequestHelper.handler(ReqEntrance.key))) {
            return true;
        }
        return true;
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        MediaType contentType = outputMessage.getHeaders().getContentType();

        String handler = RequestHelper.handler(ReqEntrance.key);
        log.error("handler{}", handler);
        if (!(type instanceof Result) && ReqEntrance.gateway.eq(handler)) {
            super.writeInternal(Result.success().setData(object), Result.class, outputMessage);

        } else {
            super.writeInternal(object, type, outputMessage);
        }

        //boolean isResultJson = Objects.equals(contentType, MediaType.APPLICATION_JSON)
        //        || Objects.equals(contentType, MediaType.APPLICATION_JSON_UTF8);

    }
}
