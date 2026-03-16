package com.example.agent.tools;

import com.example.agent.mcp.McpHttpClient;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GitHubMcpToolsTest {

    @Test
    void should_call_mcp_tool() {
        McpHttpClient mcp = mock(McpHttpClient.class);

        when(mcp.callTool(eq("issue_write"), anyMap()))
                .thenReturn(Mono.just(Map.of(
                        "content", java.util.List.of(
                                Map.of("type", "text", "text", "{\"id\":\"123\",\"url\":\"https://github.com/x/y/issues/42\"}")
                        )
                )));

        GitHubMcpTools tools = new GitHubMcpTools(mcp, "philippelin", "lab_mcp_ai_agent_springboot");

        String result = tools.createIssue("Test title", "Test body");

        assertTrue(result.contains("Issue created successfully"));
        verify(mcp, times(1)).callTool(eq("issue_write"), anyMap());
    }
}