# Spring AI MCP Games Demo

A Spring Boot application that exposes game reward information as an MCP server. This project demonstrates how to create an MCP server using Spring AI that can be integrated with AI assistants and workflow automation tools like n8n.

## Features

- **MCP Server**: Exposes game reward tools via the MCP protocol
- **Game Rewards API**: Provides detailed reward information for board games
- **Spring Boot Integration**: Built with Spring Boot 3.5.7 and Spring AI 1.0.2

## Prerequisites

- **Java 25** or higher
- **Maven 3.6+** or use the included Maven Wrapper (`./mvnw`)
- **Lombok**: Required annotation processor for build

## Getting Started

### Building the Project

```bash
# Using Maven Wrapper (recommended)
./mvnw clean package

# Or using system Maven
mvn clean package
```

### Running the Application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or run the JAR directly
java -jar target/games-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## Available Tools

### getGameRewards

Returns reward details and team ownership information for board games.

**Parameters**:
- `gameName` (String): The name of the game
  - Valid options: `Monopoly`, `Chess`, `Scrabble`

**Examples**:

```json
// Monopoly
{
  "jsonrpc": "2.0",
  "id": 1,
  "method": "tools/call",
  "params": {
    "name": "getGameRewards",
    "arguments": {
      "gameName": "Monopoly"
    }
  }
}
```

## Integration with n8n

This MCP server can be integrated with n8n workflow automation. The project includes a ready-to-use n8n workflow.

### n8n Workflow Files

The project includes a complete n8n workflow that integrates the MCP server with a conversational AI agent:

**`n8n/games.json`** - Complete workflow featuring:
- **MCP Client Tool**: Connects to `http://host.docker.internal:8080/sse` to call the `getGameRewards` tool
- **OpenAI Agent**: GPT powered conversational agent with chat memory
- **Chat Interface**: Webhook-based chat trigger for interactive queries
- **RAG System**: Vector store with embeddings for retrieving game feature information
- **Data Import**: Automated import of game features from the data file

**`n8n/games.txt`** - Game features catalog containing:
- Information about Poker, Blackjack, Roulette, Slots, and Bingo games
- Reward distribution percentages (chips, coins, gems, XP)
- Team ownership for each game
- Detailed game descriptions

The workflow allows users to chat with an AI assistant that can:
- Query real-time game rewards via the MCP server (Monopoly, Chess, Scrabble)
- Retrieve historical game features from the RAG system (Poker, Blackjack, etc.)
- Maintain conversation context and memory

### Importing the n8n Workflow

1. Start the Spring Boot application:
   ```bash
   ./mvnw spring-boot:run
   ```

2. Import the workflow in n8n:
   - Open n8n UI
   - Go to **Workflows** → **Import from File**
   - Select `n8n/games.json`
   - Update the MCP endpoint URL if needed (default: `http://host.docker.internal:8080/sse`)

3. Configure credentials:
   - Add your OpenAI API credentials
   - Ensure the MCP server is accessible from n8n's Docker network

4. Activate and use the workflow:
   - The workflow provides a chat interface to query game information
   - It uses both the MCP server for real-time data and RAG for historical game features

### Quick Integration Steps

If setting up manually:
1. Start the application on your host machine
2. Configure n8n HTTP Request node to use: `http://host.docker.internal:8080/sse`
3. Initialize SSE connection to get session ID
4. Send MCP protocol messages to `/mcp/message?sessionId={sessionId}`

## Project Structure

```
.
├── n8n/                                       # n8n workflow files
│   ├── games.json                             # n8n workflow definition
│   └── games.txt                              # Game features data for RAG
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/clubulprogramatorilor/tools/games/
│   │   │       ├── GamesApplication.java          # Main application class
│   │   │       ├── mcp/
│   │   │       │   └── McpToolConfiguration.java  # MCP tool configuration
│   │   │       └── tools/
│   │   │           └── ToolsConfiguration.java    # Game reward tool implementation
│   │   └── resources/
│   │       └── application.properties              # Application configuration
│   └── test/
│       └── java/
│           └── com/clubulprogramatorilor/tools/games/
│               └── GamesApplicationTests.java      # Application tests
├── pom.xml                                    # Maven dependencies
└── README.md                                  # This file
```

## Dependencies

- **Spring Boot** 3.5.7
- **Spring AI** 1.0.2 (MCP Server WebMVC Starter)
- **Lombok** 1.18.42
- **Maven Compiler Plugin** (configured for Java 25)