FROM golang:1.16 AS build
WORKDIR /go/src
COPY . .
RUN go build -o app ./

FROM gcr.io/distroless/static:nonroot
COPY --from=build /go/src/app /
ENTRYPOINT ["/app"]
