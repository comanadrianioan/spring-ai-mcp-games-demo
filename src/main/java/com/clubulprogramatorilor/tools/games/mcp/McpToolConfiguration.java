package com.clubulprogramatorilor.tools.games.mcp;

import java.util.List;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clubulprogramatorilor.tools.games.tools.ToolsConfiguration;

@Configuration
public class McpToolConfiguration {

    @Bean
    public List<ToolCallback> toolCallbacks(ToolsConfiguration toolsConfiguration) {
        return List.of(ToolCallbacks.from(toolsConfiguration));
    }
}
