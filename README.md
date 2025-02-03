# Chat-Service Architecture

## Overview
The **chat-service** is a Spring Boot-based microservice leveraging [Spring AI](https://github.com/spring-projects/spring-ai) to connect to OpenAI for question answering and content generation. The key goal of this service is to provide **meal planning recommendations** (via Retrieval-Augmented Generation, or RAG) by combining information from two data sources:
1. **Recipe Blog Posts** – Scraped from various cooking/recipe websites.
2. **User Preferences** – Stored in a DynamoDB table, reflecting feedback and likes/dislikes.

Upstream services can call the **chat-service** API to generate meal plans for a single parent who needs to plan weekly meals.

---

## Components

### 1. Chat-Service (Spring Boot)
- **API Layer**: Exposes REST endpoints for chat and meal planning prompts.
- **Business Logic**: Implements the RAG pipeline, orchestrates data retrieval from recipe knowledge base and user preferences, and integrates with OpenAI via Spring AI.
- **Model/Entities**: Defines request/response objects, as well as internal models to represent recipes and user preferences.

### 2. Spring AI Integration
- **OpenAI Connection**: Responsible for orchestrating calls to OpenAI for text completion or chat-based LLM tasks.
- **Prompt Engineering**: Embeds relevant information (scraped recipes + user preferences) into the prompt sent to OpenAI to generate context-aware meal suggestions.

### 3. Recipe Data Store (Scraped Blog Posts)
- **Scraping Process**: A separate scraper process (or scheduled job) fetches blog posts from recipe websites.
- **Data Transformation**: Recipes are parsed to extract:
    - **Ingredients**
    - **Cooking duration**
    - **Other meta-information** (e.g., cuisine type, dietary restrictions)
- **Storage**: Could be stored in an internal database, search index, or vector store to facilitate semantic retrieval.

### 4. DynamoDB (Preferences Store)
- **Schema**: Contains feedback documents reflecting user preferences, e.g. likes/dislikes or other meal planning constraints.
- **Usage**: The RAG pipeline fetches these preference documents as additional context for the LLM to personalize meal suggestions.

### 5. Retrieval-Augmented Generation (RAG) Pipeline
1. **Receive Prompt**: The chat-service receives a user or system prompt requesting meal planning advice or recipe suggestions.
2. **Retrieve Relevant Documents**:
    - **Recipe Documents**: Fetched from the recipe repository or vector store.
    - **User Preferences**: Fetched from the DynamoDB table to incorporate user context.
3. **Build Context**: Combine relevant recipe data + preferences into a single context bundle.
4. **Call OpenAI**: Pass the combined context and user prompt to OpenAI through Spring AI.
5. **Generate Response**: Return the LLM output to the user.

---

## High-Level Architecture Diagram

```mermaid
flowchart LR
    subgraph Data Ingestion
        A[Web Scraper] --> B[Recipe Repository / Vector Store]
    end

    subgraph Preferences
        D[DynamoDB Preferences Store]
    end

    subgraph Chat Service
        E[Chat-Service API] --> F[RAG Pipeline]
        F -- retrieve recipes --> B
        F -- retrieve preferences --> D
        F -- calls OpenAI --> G[OpenAI API]
        G --> F
        F --> E
    end