# Bank AI RAG — Java 21

Educational production-style project for learning:

- Spring Boot + Java 21
- PostgreSQL + pgvector
- document ingestion/chunking
- embeddings
- vector similarity search
- LLM API / RAG
- later: Java GPU programming and training

## Run

1. Start PostgreSQL:

    docker compose up -d

2. Set your OpenAI key:

    Linux/macOS:
    `export OPENAI_API_KEY=...`

    PowerShell:
    `$env:OPENAI_API_KEY="..."`

3. Start Spring Boot:

    `mvn spring-boot:run`

## Current state

The project intentionally stops at the boundary before the embedding provider. This makes the next lesson explicit:

TXT -> chunks -> embedding -> pgvector -> retrieval -> prompt -> OpenAI -> answer.

After that we will replace the embedding implementation with a GPU implementation and benchmark CPU vs GPU.

## Important

Do not put an OpenAI API key into source code or git.
