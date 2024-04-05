export interface ParsedResponse {
    id: string;
    metadata: string;
    parsedData: {
      [key: string]: string;
    };
}