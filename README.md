# Spring AI+Ollama+pgvectorʵ�ֱ���RAG

## ǰ��

����Ŀ��Ϊ���ڱ�������ʵ��https://github.com/jianyuan1991/ragdemo���������Ǳ�����ʹ����Ruo-Yi�Ŀ�Դ���ڴ����õĻ�����ʵ��github�е�����

github������֮ǰд��һƪ[Spring AI+Ollama���ػ����](https://www.bxmdm.com/archives/2024030801)�����£�������Ļ����Ķ���jdk��������1.8->1.17����maven�Ľ��������°�->3.8.4����

## RAG���
RAG��Ŀǰ��ģ��Ӧ����ص�һ�׽���������������м�����ǿ�����ڴ�����ģ����ʱЧ�Ժͻþ��Ⱦ����ԣ�ʹ��RAG���������������������ӱ���֪ʶ����������Ҫ�������Ϣ���ڽ������Ϣ���prompt�������ĵ�һ���֣����ɴ�ģ�͸���prompt���лظ������ξ͹���һ��demo��ʹ��RAG��������һ���ĵ��ʴ��Ӧ�á���ͼ��ʾ��RAG�ĵ��ʴ���������̴��·ֳ������׶Σ�

1������׼���������ı�����ͨ��embeddingģ��ת���ı����������洢���������ݿ��С�

2���û����ʣ����û�������ı�ͨ��embeddingģ��ת�������ı������������������н��������������õ�һЩ�ı����䣬�����������ı�������װ��promptȥ���ô�ģ������ô𰸡�

```mermaid
graph LR  
    
    user[User] --> question[����]
    doc[�ı�����] --> embedding[Embedding Model]  
    embedding --> docVector[�ı�����]
    docVector --> VectorDatabase[�������ݿ�]
    question[����] --> embedding[Embedding Model] 
    embedding[Embedding Model]  --> questionVector[��������]
    questionVector[��������] --> VectorDatabase[�������ݿ�]
    VectorDatabase[�������ݿ�] --> docParagraph[�ı�����]
    docParagraph[�ı�����] --> prompt[��ʾ��]
    prompt[��ʾ��] --> llm[��ģ��]
    llm[��ģ��] --> answer[��]
    answer[��] --> user[User]
    
    style doc fill:#98FB98;
    style docVector fill:#98FB98;
    style user fill:#FF8C00;
    style question fill:#FF8C00;
    style questionVector fill:#FF8C00;
    style docParagraph fill:#FF8C00;
    style prompt fill:#FF8C00;
    style answer fill:#FF8C00;
```

����demo�У��������̵�ҵ���߼���ͨ��spring ai��ʵ�֣�spring ai֧�ֵ���Ollama��ʵ��chat��embedding��֧��pgvector����Ϊ�������ݴ洢������������ѡ���ģ�ͺ����ݿ���Ϣ���£�

ģ�����й��ߣ�Ollama

embeddingģ�ͣ� mofanke/dmeta-embedding-zh������֧�ֱȽϺã�

��ģ�ͣ�qwen:7b������֧�ֱȽϺã�

�������ݿ⣺pgvector��postgresql��


## ����׼��

����embeddingģ��:

```powershell
ollama pull  mofanke/dmeta-embedding-zh
```

![��װdmeta-embedding-zh���](./img/��װdmeta-embedding-zh���.png)



### pgvector

pgvector��postgresql��һ����չ��ʹ��postgresql�ܹ��洢�������������ݣ�pgvector �ṩ 2 �����͵�������IVFFlat �� HNSW�����ǽ�������� ��ANN�� �������������Լӿ����������������ٶȡ����������ǵ���Ҫ����

![IVFFlat��HNSW������](./img/IVFFlat��HNSW����.png)

����pgvector ���������������ڼ������ƶȵ���������� <-> �C ŷ����þ��롢<#> �C ���ڻ���<=> �C ���Ҿ���

����demo����docker��װpgvector:

```
docker run --name pgvector \
    -e POSTGRES_PASSWORD=postgres \
    -p 5432:5432 \
	-d pgvector/pgvector:pg16
```

Ҫʹ��pgvector��������Ҫ��װpostgresql�ģ��������ڱ�����5432�˿ڱ�ռ�ˣ������Ҹó�5433�ˣ���������ò鿴�����ļ��е�`src/main/resources/application.yml`�������û���������������ü��ɣ�

����**postgresql**�������У�
`psql -h localhost -p 5433 -U postgres`

���ڱ��˲�̫����docker�����Բο��ٷ��ĵ��ķ�����windows��ֱ�����ز����==�ٷ��ĵ����Ǻð�==��

**Windows**
Ensure C++ support in Visual Studio is installed, and run:
```
call "C:\Program Files\Microsoft Visual Studio\2022\Community\VC\Auxiliary\Build\vcvars64.bat"
```
Note: The exact path will vary depending on your Visual Studio version and edition

Then use nmake to build:
```
set "PGROOT=C:\Program Files\PostgreSQL\16"//�Լ����ص�Postgresql·��
cd %TEMP%
git clone --branch v0.7.2 https://github.com/pgvector/pgvector.git
cd pgvector
nmake /F Makefile.win
nmake /F Makefile.win install
See the installation notes if you run into issues
```

**ע�⣺�˴���Ҫ����ԱȨ��**

**Getting Started**
Enable the extension (do this once in each database where you want to use it)
```sql
CREATE EXTENSION vector;
```
�᷵�ش����ɹ�����ʾ��Ϣ

## ����

### �ϴ�����

׼����springAI�����Ϣ��Ϊ��������[Spring AI :: Spring AI Reference](https://docs.spring.io/spring-ai/reference/index.html)���������£�

```
Spring AI��Ŀּ�ڼ򻯰����˹����ܹ��ܵ�Ӧ�ó���Ŀ�������������ɲ���Ҫ�ĸ����ԡ�
����Ŀ�������� Python ��Ŀ�м�ȡ��У����� LangChain �� LlamaIndex���� Spring AI ��������Щ��Ŀ��ֱ����ֲ�� ����Ŀ�����������ǣ���һ������ʽ AI Ӧ�ó��򽫲��������� Python ������Ա�����ҽ���������������޴����ڡ�
Spring AI �ĺ������ṩ������Ϊ���� AI Ӧ�ó���Ļ����� ��Щ������ж���ʵ�֣�ֻ�����ٵĴ�����ļ������ɽ��������
Spring AI �ṩ���¹��ܣ�
1.֧��������Ҫ��ģ���ṩ�̣���OpenAI��Microsoft��Amazon��Google��Huggingface��
2.֧�ֵ�ģ�����Ͱ���������ı���ͼ�񣬻��и����������ڿ����С�
3.�� AI �ṩ�̵Ŀ���ֲ API�����������Ƕ��ģ�͡�֧��ͬ������ API ѡ���֧�������Է����ض���ģ�͵Ĺ��ܡ�
4.�� AI ģ�����ӳ�䵽 POJO��
5.֧��������Ҫ���������ݿ��ṩ�̣����� Azure Vector Search��Chroma��Milvus��Neo4j��PostgreSQL/PGVector��PineCone��Qdrant��Redis �� Weaviate
6.�� Vector Store �ṩ����Ŀ���ֲ API������һ������ SQL ����ӱԪ���ݹ����� API���� API Ҳ�ǿ���ֲ�ġ�
7.��������
8.���� AI ģ�ͺ�ʸ���洢�� Spring Boot �Զ����ú���������
9.�������ݹ��̵� ETL ���
ͨ���˹��ܼ���������ʵ�ֳ������������硰�ĵ��ʴ𡱻����ĵ����족��
������ṩ�� AI ������� Spring AI �еı�ʾ�ĸ߼�������
�����š����ֽ�������δ�����һ�� AI Ӧ�ó��� �������ֽ������Դ���Ϊ���ĵķ�������̽��ÿ������ͳ���������
```



ʹ��postman���ýӿ�:

![postman�����ļ��ϴ�](./img/postman�����ļ��ϴ�.png)

�����޸��˴��룬��Ȼ�ᱨ400��Bad request��·����src/main/java/com/bxmdm/ragdemo/controller/IndexController.java��

������ʹ��postman��ʱ��==��Ҫ��дkeyֵFile==
![IndaexController���޸�](./img/IndaexController���޸�.jpg)

�ϴ����ļ���D����
![�����ļ��ĵط�](./img/�����ļ��ĵط�.png)

����demoʹ���������ݿ���pgvector����Ŀ������spring ai������pgvector�Զ�����vector_store����ȻҲ�����ֶ���ǰ����:

```sql
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store (
	id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
	content text,
	metadata json,
	embedding vector(768) # 768 ��embeddingģ��nomic-embed-text��ά��
);

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);
```



���Թ�������������ݿ��ѯvector_store�������:

![pgvector���ݲ�ѯ](./img/pgvector���ݲ�ѯ.png)

ʹ��postgresql��query����

### �ʴ����

����׼���׶��Ѿ�������ɣ����������Բ����ʴ�ʹ��postman���ýӿ�:

![postman�����ʴ�](./img/postman�����ʴ�.png)

����CPU���д�ģ�ͻ��ǱȽ��������������Ƕ������ˡ�

## ����ʵ��

### ����׼��

����demo�ı����ݶ�ȡtxt�ļ���spring ai��ʵ����TextReader���ڶ�ȡtxt�ļ�������Դ�����£��Ƚϼ򵥣�û�ж��ı����ݽ��н������ı����ݵĽ�����RAG�к���Ҫ��ֱ��Ӱ��RAG���ı���Ϣ������Ч������

```java
String document = StreamUtils.copyToString(this.resource.getInputStream(), this.charset);

// Inject source information as a metadata.
this.customMetadata.put(CHARSET_METADATA, this.charset.name());
this.customMetadata.put(SOURCE_METADATA, this.resource.getFilename());

return List.of(new Document(document, this.customMetadata));
```

����demo����ʵ��TXT�Ķ�ȡ�����ݻس������ı����ݽ��зֶΣ������ô���ģʽ�Էֶ����ݽ����ĵ����֣������ȸ���TextReader���룬�½���ParagraphTextReader��ʵ���࣬����ʵ�ִ������£�

```java
/**
* Ĭ�ϴ��ڴ�С��Ϊ1
*/
private static final int DEFAULT_WINDOW_SIZE = 1;

/**
* ���ڴ�С��Ϊ��������������ڹ�����ȡ
*/
private int windowSize = DEFAULT_WINDOW_SIZE;

public static final String START_PARAGRAPH_NUMBER = "startParagraphNumber";
public static final String END_PARAGRAPH_NUMBER = "endParagraphNumber";

/**
* ��ȡ�ı�����,�����ݻ��н��зֶ�,���ô���ģʽ,����Ϊ���������
*
* @return �ĵ���Ϣ�б�
*/
@Override
public List<Document> get() {
    try {

        List<Document> readDocuments = new ArrayList();
        String document = StreamUtils.copyToString(this.resource.getInputStream(), this.charset);

        // Inject source information as a metadata.
        this.customMetadata.put(CHARSET_METADATA, this.charset.name());
        this.customMetadata.put(SOURCE_METADATA, this.resource.getFilename());
		
        //�ı����ݸ��ݻس������зֶ�
        List<String> paragraphs = Arrays.asList(document.split("\n"));

        //���ô��ڻ�����ȡ�ֶ�����
        int startIndex = 0;
        int endIndex = startIndex + this.windowSize;
        if (endIndex > paragraphs.size()) {
            readDocuments.add(this.toDocument(paragraphs, startIndex + 1, paragraphs.size()));
        } else {
            for (; endIndex <= paragraphs.size(); startIndex++, endIndex++) {
                readDocuments.add(this.toDocument(ListUtil.sub(paragraphs, startIndex, endIndex), startIndex + 1, endIndex));
            }
        }
        return readDocuments;
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
}

/**
 * ��װ������ĵ�
 * @param paragraphList ���������б�
 * @param startParagraphNum ��ʼ�������
 * @param endParagraphNum �����������
 * @return �ĵ���Ϣ
 */
private Document toDocument(List<String> paragraphList, int startParagraphNum, int endParagraphNum) {
    Document doc = new Document(String.join("\n", paragraphList));
    doc.getMetadata().putAll(this.customMetadata);
    doc.getMetadata().put(START_PARAGRAPH_NUMBER, startParagraphNum);
    doc.getMetadata().put(END_PARAGRAPH_NUMBER, endParagraphNum);
    return doc;
}
```



�����ļ��ϴ��ӿ���������TXT�ļ������ִ�������:

```java
@PostMapping("/upload")
public ResponseEntity upload(@RequestBody MultipartFile file) {
    documentService.uploadDocument(file);
    return ResponseEntity.ok("success");
}
```



documentService������ʵ���ļ����ϴ�����,��ʹ��spring ai�е�VectorStore�洢���ݣ����ִ�������:

```java
@Autowired
private VectorStore vectorStore;

private static final String PATH = "D:\\demo\\ai\\path\\";

/**
 * ʹ��spring ai����txt�ĵ�
 *
 * @param file
 * @throws MalformedURLException
 */
public void uploadDocument(MultipartFile file) {
    //����file������
    String textResource = file.getOriginalFilename();
    //�ж��ļ��Ƿ���TXT
    if (!textResource.endsWith(".txt")) {
        throw new RuntimeException("ֻ֧��txt��ʽ�ļ�");
    }
    String filepath = PATH + textResource;
    File file1 = new File(filepath);
    if(file1.exists()){
        throw new RuntimeException("�ļ��Ѵ���");
    }
    try {
        file.transferTo(file1);
    } catch (Exception e) {
        e.printStackTrace();
    }
    List<Document> documentList = paragraphTextReader(file1);
    vectorStore.add(documentList);
}

private List<Document> paragraphTextReader(File file) {
    List<Document> docs = null;
    try {
        //�����������û������ڴ�СΪ5
        ParagraphTextReader reader = new ParagraphTextReader(new FileUrlResource(file.toURI().toURL()), 5);
        reader.getCustomMetadata().put("filename", file.getName());
        reader.getCustomMetadata().put("filepath", file.getAbsolutePath());
        docs = reader.get();
    } catch (Exception e) {
        e.printStackTrace();
    }
    return docs;
}
```



### �û�����

�û�������Ҫ�ȸ����������������������Ӧ�Ķ��䣬����demo���ı�����ʹ�û������ڵ�ģ�ͽ��ж�ȡ��������Ҫ�������������һ�κϲ������벿�����£�

```java
/**
 * �ϲ��ĵ��б�
 * @param documentList �ĵ��б�
 * @return �ϲ�����ĵ��б�
 */
private List<Document> mergeDocuments(List<Document> documentList) {
	List<Document> mergeDocuments = new ArrayList();
	//�����ĵ���Դ���з���
	Map<String, List<Document>> documentMap = documentList.stream().collect(Collectors.groupingBy(item -> ((String) item.getMetadata().get("source"))));
	for (Entry<String, List<Document>> docListEntry : documentMap.entrySet()) {
		//��ȡ���Ķ����������
		int maxParagraphNum = (int) docListEntry.getValue()
				.stream().max(Comparator.comparing(item -> ((int) item.getMetadata().get(END_PARAGRAPH_NUMBER)))).get().getMetadata().get(END_PARAGRAPH_NUMBER);
		//����������������빹��һ�����ںϲ�����Ŀ�����
		String[] paragraphs = new String[maxParagraphNum];
		//���ڻ�ȡ��С���俪ʼ����
		int minParagraphNum = maxParagraphNum;
		for (Document document : docListEntry.getValue()) {
			//�ĵ����ݸ��ݻس����зֶ�
			String[] tempPs = document.getContent().split("\n");
			//��ȡ�ĵ���ʼ�������
			int startParagraphNumber = (int) document.getMetadata().get(START_PARAGRAPH_NUMBER);
			if (minParagraphNum > startParagraphNumber) {
				minParagraphNum = startParagraphNumber;
			}
			//���ĵ������б������ϲ�����������
			System.arraycopy(tempPs, 0, paragraphs, startParagraphNumber-1, tempPs.length);
		}
		//�ϲ�����ȥ����ֵ,������ĵ�����
		Document mergeDoc = new Document(ArrayUtil.join(ArrayUtil.removeNull(paragraphs), "\n"));
		//�ϲ�Ԫ����
		mergeDoc.getMetadata().putAll(docListEntry.getValue().get(0).getMetadata());
		//����Ԫ����:��ʼ�������
		mergeDoc.getMetadata().put(START_PARAGRAPH_NUMBER, minParagraphNum);
		//����Ԫ����:�����������
		mergeDoc.getMetadata().put(END_PARAGRAPH_NUMBER, maxParagraphNum);
		mergeDocuments.add(mergeDoc);
	}
	return mergeDocuments;
}

/**
	 * ���ݹؼ�������������
	 *
	 * @param keyword �ؼ���
	 * @return �ĵ��б�
	 */
public List<Document> search(String keyword) {
    return mergeDocuments(vectorStore.similaritySearch(keyword));
}
```



������������װprompt�͵��ô�ģ�͵ķ��������벿������:

```java
/**
 * �ʴ�,�����������ݻش�
 * @param message ��������
 * @return �ش�����
 */
public String chat(String message) {
	//��ѯ��ȡ�ĵ���Ϣ
	List<Document> documents = search(message);
	
	//��ȡ�ı�����
	String content = documents.stream()
			.map(Document::getContent)
			.collect(Collectors.joining("\n"));
	
	//��װprompt�����ô�ģ��
	String chatResponse = ollamaChatClient.call(getChatPrompt2String(message, content));
	return chatResponse;
}

/**
 * ��ȡprompt
 * @param message ��������
 * @param context ������
 * @return prompt
 */
private String getChatPrompt2String(String message, String context) {
	String promptText = """
			���ý����������ݻش�"%s":
			%s
			""";
	return String.format(promptText, message, context);
}
```



�ٹ���һ���ӿ������������ʣ����벿������:

```java
@GetMapping("/chat")
public ResponseEntity chat(@RequestParam String message) {
    return ResponseEntity.ok(documentService.chat(message));
}
```



������Ϣ

�����Ҫ�����ļ��������������ݿ⡢embedding�ʹ�ģ��chat��Ϣ��application.yml���ò�������:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://192.168.3.220:5432/postgres
    username: postgres
    password: postgres
  ai:
    vectorstore:
      pgvector:
      	##embedding������ά�ȣ������768�Ǹ���nomic-embed-text���ص�����ά�����õ�
        dimensions: 768
    ollama:
      base-url: http://localhost:11434
      chat:
        model: qwen:7b
      embedding:
        model: nomic-embed-text
```



����embeddingģ�͵�����ά�ȿ���дһ�����Է�������ȡ�����벿�����£�

```java
@Autowired
private EmbeddingClient embeddingClient;

@Test
void embeddingDimensionsTest(){
    //��ӡembeddingģ�͵�ת��������ά��
	System.out.println(embeddingClient.dimensions());
}
```



## �ܽ�

Spring AI���Ollama��pgvector�ܷܺ���Ĺ���һ��RAG�ĵ��ʴ�Ӧ�á�RAGЧ���ܵ��ĵ��Ĳ�֡�embedding������������promptģ�塢��ģ����Щ���ص�Ӱ�죬Ҫ���Ż��Ļ����Դ���Щ����ȥ���ǡ�



**�ο���**

[pgvector/pgvector: Open-source vector similarity search for Postgres (github.com)](https://github.com/pgvector/pgvector)

[PGvector :: Spring AI Reference](https://docs.spring.io/spring-ai/reference/api/vectordbs/pgvector.html)

[Ollama Chat :: Spring AI Reference](https://docs.spring.io/spring-ai/reference/api/clients/ollama-chat.html)

[Ollama Embeddings :: Spring AI Reference](https://docs.spring.io/spring-ai/reference/api/embeddings/ollama-embeddings.html)

[qwen (ollama.com)](https://ollama.com/library/qwen)

[mofanke/dmeta-embedding-zh (ollama.com)](https://ollama.com/mofanke/dmeta-embedding-zh)