getstockdata <- function(name, settings = c("Open", "High", "Low", "Close", "Volume"), asdate = TRUE, header = FALSE){

	# setwd("C:/Users/IBM_ADMIN/Desktop")

	if(require(tseries)){
	} else {
	    print("trying to install tseries package")
	    install.packages("tseries")
	    if(require(tseries)){
	        print("tseries installed and loaded")
	    } else {
	        stop("could not install tseries")
	    }
	}

	data <- get.hist.quote(name, quote = settings)

	if(asdate) {
		data <- as.data.frame(data)
	}

	write.table(data,paste(toString(name),".txt",sep=""),sep=",",col.names=header)

	print(paste("Data written to ",getwd(),"/",name,".txt",sep=""))
}
