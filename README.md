# Graphql course

# http vs graphql verb comparison
- PostMapping(value="books") :: RequestMapping(value="books", method=RequestMethod.POST)
- PutMapping(value="books") :: RequestMapping(value="books", method=RequestMethod.PUT)
- DeleteMapping(value="books") :: RequestMapping(value="books", method=RequestMethod.DELETE)
  -  ## graphql- equivalent 
  - MutationMapping(value="books") :: SchemaMapping(typeName="Mutation", field="books") 

- GetMapping(value="books") :: RequestMapping(value="books", method=RequestMethod.GET)
  -   ## graphql- equivalent
  - QueryMapping(value="books") :: SchemaMapping(typeName="Query", field="books")

- graphql websocket
- SubscriptionMapping(value="books") :: SchemaMapping(typeName="Subscription", field="books")


