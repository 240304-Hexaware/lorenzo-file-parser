import { NgIf, NgFor, JsonPipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { FileService } from '../../services/file/file.service';

@Component({
  selector: 'app-query',
  standalone: true,
  imports: [ NgIf, NgFor, FormsModule, JsonPipe ],
  templateUrl: './query.component.html',
  styleUrl: './query.component.css'
})
export class QueryComponent {

  fieldName: string = '';
  fieldValue: string = '';
  parsedDataFromQuery: any[] = [];

  constructor(private fileService: FileService){}
  queryRecords() {
    if (this.fieldName && this.fieldValue) {
      this.fileService.getParsedDataFromFieldAndValue(this.fieldName, this.fieldValue)
        .subscribe(data => {
          // Handle the response data as needed
          this.parsedDataFromQuery = data;
        });
    }
  }

}
