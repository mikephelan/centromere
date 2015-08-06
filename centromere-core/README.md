# Centromere Core

Components for creating database repositories, data importers, and REST web services.  These components are data-model-agnostic and can be used to fit any data type.  For genomic data-specific model classes, see the Centromere-Model module.

## Quick Start

### Note

This project is still in early development.  Components can and will change, and the documentation below may become out of sync with the latest build of the project.  I will do my best to keep the documentation up-to-date.

### Configuration

The easiest way to configure Centromere is to use the `@AutoConfigureCentromereWeb` annotation in your configuration class.  This will handle all of the required web context configuration and bean registration.

```java
@Configuration
@AutoConfigureCentromereWeb
@ComponentScan(basePackages = { "me.woemler.myapp.centromere" })
public class ApplicationConfig {
	// Additional config steps go here.
}
```

### Data Model

Centromere is designed for use with the work-in-progress Oncoblocks data model, but is not limited to this specification.  The first step in implementing a data warehouse with Centromere is to design a data model and create representational `Model` classes for each resource:

```java
/* Simple MongoDB representation of an Entrez Gene record  */
@Filterable
@Document(collection = "genes")
public class Gene implements Model<String> {

	@Id private String id;
	@Indexed private Long entrezGeneId;
	@Indexed private String primaryGeneSymbol;
	private Integer taxId;
	private String locusTag;
	private String chromosome;
	private String chromosomeLocation;
	private String description;
	private String geneType;
	private Set<String> aliases;

	public Gene() { }

	/* Getters and Setters */
}
```

The `Model` interface ensures that the entity has a defined primary ID, of the specified type (in this case, a `String` representation of a MongoDB `ObjectID`).  The `@Filterable` annotation identifies the entity class as being a candidate for field-filtering operations in the web services layer.

### Repository

Choose the appropriate `RepositoryOperations` implementation, based upon your database technology, and define a class to handle repository operations of your entity classes:

```java
@Repository
public class GeneRepository extends GenericMongoRepository<Gene, String> {
	@Autowired
	public GeneRepository(MongoTemplate mongoTemplate) {
		super(mongoTemplate, Gene.class);
	}
}
```

The default repository class implementations include most of the same method signatures as Spring Data repository classes, with some additions to support dynamic queries.  Custom repository methods can also be implemented in these classes, to support specific use-cases.

### Service

The service layer passes query requests and responses between the web services and repository layers.  The buffer that this layer provides allows for repository implementations to change without affecting the API implementation.  Implementations of `ServiceOperations` support this by allowing the remapping of web service request parameters to database implementation-specific parameters.  The default implementation simply passes requests, without modification, from one layer to the next:

```java
@Service
public class GeneService extends GenericService<Gene, String> {
	@Autowired
	public GeneService(GeneRepository repository) {
		super(repository);
	}
}
```

### Web Services

The web services (controller) layer handles HTTP requests and routes them to the appropriate entity service.  Web service controllers support standard CRUD operations via `GET`, `POST`, `PUT`, `DELETE`, and `OPTIONS` requests by default, and can be made read-only by overriding the default methods, or secured using Spring Security and the provided token-authenitcation tools. Implementations of `ControllerOperations` vary primarily based upon the method of handling dynamic `GET` queries.  

```java
@Controller
@RequestMapping(value = "/genes")
public class GeneController extends EntityQueryController<Gene, String> {

	@Autowired
	public GeneController(GeneService service) {
		super(service, new GeneAssembler());
	}

	public static class GeneAssembler extends ResourceAssemblerSupport<Gene, FilterableResource> {
    	public GeneAssembler() {
    		super(GeneController.class, FilterableResource.class);
    	}

    	@Override public FilterableResource toResource(Gene gene) {
    		FilterableResource<Gene> resource = new FilterableResource<Gene>(gene);
    		resource.add(linkTo(GeneController.class).slash(gene.getId()).withSelfRel());
    		return resource;
    	}
    }

}
```

## RESTful API

Once your application is up-and-running, you can reach your data using the relative root URLs specified in your controller classes:

Method | URI | Description
-------|-----|------------
`GET` | `/genes` | Fetches all Genes
`GET` | `/genes/{id}` | Fetch a single Gene by ID
`POST` | `/genes` | Creates a new Gene record
`PUT` | `/genes/{id}` | Updates an existing Gene
`DELETE` | `/genes/{id}` | Deletes an existing Gene
`OPTIONS` | `/genes` | Fetches info about the available Gene operations

#### Searching

You can perform queries using one or more entity attributes:

```
GET /genes?alias=akt&geneType=protein-coding
```

#### Paging and Sorting

Requests can return results that are both paginated and sorted:

```
GET /genes?size=100&page=2&sort=entrezGeneId,asc
```

#### Field Filtering

Requests can specify which entity fields will be returned, or excluded:

```
GET /genes?fields=entrezGeneId,primaryGeneSymbol
GET /genes?exclude=description,links
```

#### Hypermedia

All entity response objects include embedded HAL-formatted links to related entities, allowing for easy resource discovery:

```
Request:
GET /hgu133/rma

Response:
200 OK
[
	{
		sampleId: 123,
		entrezGeneId: 207,
		dataFileId: 43,
		value: 103.12
		links: [
			{ rel: "self", href: "http://myapp/hgu133/rma?sampleId=123&entrezGeneId=207&dataFileId=43"  },
			{ rel: "sample", href: "http://myapp/samples/123"  },
			{ rel: "gene", href: "http://myapp/genes/207"  },
			{ rel: "data_file", href: "http://myapp/datafiles/43"  }
		]
	}, ...
]
```

#### Compression

All responses can be GZIP compressed by including the `Accept-Encoding: gzip,deflate` header.